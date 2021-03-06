package com.mtk.songsOrganizer.dbLayer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.result.UpdateResult;
import com.mtk.songsOrganizer.common.annotations.Entity;
import com.mtk.songsOrganizer.common.annotations.IdField;
import com.mtk.songsOrganizer.common.annotations.MandatoryField;
import com.mtk.songsOrganizer.common.dto.DTOObj;
import com.mtk.songsOrganizer.common.dto.ParamDTO;
import com.mtk.songsOrganizer.common.dto.ReplyDTO;
import com.mtk.songsOrganizer.common.utils.Const;
import com.mtk.songsOrganizer.common.utils.Utils;

public class DBOperations {

	private static Map<String, Object> syncMap = new HashMap<>();

	public ReplyDTO addUpdate(ParamDTO paramDTO) {
		ReplyDTO replyDTO = new ReplyDTO();
		DTOObj dtoObj = paramDTO.getDtoObj();
		if (paramDTO == null || dtoObj == null) {
			replyDTO.addErrorMsgCode(Const.MSG_CODES.INVALID_PARAMS);
			return replyDTO;
		}
		List<Field> idFields = new ArrayList<>(), mandatoryFields = new ArrayList<>(),
				fields = Utils.getAllSuperClassFields(dtoObj.getClass(), null);
		if (Utils.isEmpty(fields)) {
			replyDTO.addErrorMsgCode(Const.MSG_CODES.NO_ID_FIELD);
			return replyDTO;
		}
		for (Field field : fields) {
			if (field.isAnnotationPresent(IdField.class)) {
				idFields.add(field);
			} else if (field.isAnnotationPresent(MandatoryField.class)) {
				mandatoryFields.add(field);
			}
		}
		if (Utils.isEmpty(idFields)) {
			replyDTO.addErrorMsgCode(Const.MSG_CODES.NO_ID_FIELD);
			return replyDTO;
		}

		boolean toUpdate = false;
		Document findDoc = new Document(), sortDoc = new Document(), addUpdateDoc = new Document();
		Object val, primaryVal = null;
		String pkFldName = null;

		try {
			if (mandatoryFields.size() > 0) {
				for (Field field : mandatoryFields) {
					field.setAccessible(true);
					val = field.get(dtoObj);
					if (Utils.isEmpty(val)) {
						replyDTO.addErrorMsgCode(Const.MSG_CODES.MANDATORY_FIELD_EMPTY);
						return replyDTO;
					}
				}
			}

			for (Field field : idFields) {
				field.setAccessible(true);
				val = primaryVal = field.get(dtoObj);
				if (field.getAnnotation(IdField.class).isPrimary()) {
					pkFldName = field.getName();
					sortDoc.append(pkFldName, -1);
					if (!Utils.isEmpty(val)) {
						toUpdate = true;
						findDoc.append(pkFldName, field.get(dtoObj));
					}
				} else {
					sortDoc.append(field.getName(), 1);
					if (!Utils.isEmpty(val)) {
						findDoc.append(field.getName(), field.get(dtoObj));
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		if (Utils.isEmpty(pkFldName)) {
			replyDTO.addErrorMsgCode(Const.MSG_CODES.NO_ID_FIELD);
			return replyDTO;
		}

		// for sync
		Entity entity = dtoObj.getClass().getAnnotation(Entity.class);
		if (entity == null) {
			replyDTO.addErrorMsgCode(Const.MSG_CODES.INVALID_PARAMS);
			return replyDTO;
		}
		Object obj = syncMap.get(entity.dbName() + "_" + entity.collectionName());
		if (obj == null) {
			obj = new Object();
		}
		syncMap.put(entity.dbName() + "_" + entity.collectionName(), obj);
		synchronized (obj) {
			if (toUpdate) {
				findDoc.append(pkFldName, primaryVal);
				List<Document> docList = (List<Document>) Utils.getCollection(dtoObj.getClass()).find(findDoc)
						.into(new ArrayList<>());
				if (Utils.isEmpty(docList)) {
					replyDTO.addErrorMsgCode(Const.MSG_CODES.NO_DATA_FOUND);
					return replyDTO;
				}

				Utils.populateDocumentFromDTO(dtoObj, docList.get(0), paramDTO.getFieldSet(), false);
				UpdateResult updateResult = Utils.getCollection(dtoObj.getClass())
						.replaceOne(new Document("_id", docList.get(0).get("_id")), docList.get(0));
				if (updateResult.wasAcknowledged() && updateResult.getMatchedCount() > 0) {
					replyDTO.addMsgCode(Const.MSG_CODES.SUCCESS);
				} else {
					replyDTO.setError(true);
				}
			} else {
				List<Document> docList = (List<Document>) Utils.getCollection(dtoObj.getClass()).find(findDoc)
						.sort(sortDoc).limit(1).into(new ArrayList<>());
				if (!Utils.isEmpty(docList)) {
					try {
						primaryVal = docList.get(0).get(pkFldName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (primaryVal instanceof Integer) {
					primaryVal = (int) primaryVal + 1;
				} else if (primaryVal instanceof Long) {
					primaryVal = (long) ((long) primaryVal + 1);
				} else if (primaryVal instanceof String) {
					// for string key
					if (primaryVal == null) {
						primaryVal = "A";
					} else {
						char[] arr = ((String) primaryVal).toCharArray();
						arr[arr.length - 1] = ++arr[arr.length - 1];
						primaryVal = new String(arr);
					}
				}

				Utils.populateDocumentFromDTO(dtoObj, addUpdateDoc, null, false);
				addUpdateDoc.put(pkFldName, primaryVal);

				Utils.getCollection(dtoObj.getClass()).insertOne(addUpdateDoc);
				replyDTO.addOtherInfo(Const.LAYOUT_CONST.ID, primaryVal);
				replyDTO.addMsgCode(Const.MSG_CODES.SUCCESS);
			}
		}

		return replyDTO;
	}

	public ReplyDTO fetch(ParamDTO paramDTO) {
		ReplyDTO replyDTO = new ReplyDTO();
		DTOObj dtoObj = paramDTO.getDtoObj();
		if (paramDTO == null || dtoObj == null) {
			replyDTO.addErrorMsgCode(Const.MSG_CODES.INVALID_PARAMS);
			return replyDTO;
		}
		Document findDoc = new Document();
		Utils.populateDocumentFromDTO(dtoObj, findDoc, null, true);
		Document sortDoc = new Document();
		if (paramDTO.getOtherInfo(Const.LAYOUT_CONST.SORT_MAP) != null) {
			sortDoc.putAll((Map<String, Integer>) paramDTO.getOtherInfo(Const.LAYOUT_CONST.SORT_MAP));
		}
		List<Document> findDocList;
		if (paramDTO.getPageNo() > 0) {
			findDocList = (List<Document>) Utils.getCollection(dtoObj.getClass()).find(findDoc).sort(sortDoc)
					.skip(paramDTO.getNumRecords() * paramDTO.getPageNo()).limit(paramDTO.getNumRecords())
					.into(new ArrayList<>());
			replyDTO.setCount(Utils.getCollection(dtoObj.getClass()).count(findDoc));
			replyDTO.setLastPage(findDocList != null && findDocList.size() < replyDTO.getCount());
		} else {
			findDocList = (List<Document>) Utils.getCollection(dtoObj.getClass()).find(findDoc).sort(sortDoc)
					.into(new ArrayList<>());
		}
		if (Utils.isEmpty(findDocList)) {
			replyDTO.addErrorMsgCode(Const.MSG_CODES.NO_DATA_FOUND);
			return replyDTO;
		}
		List<DTOObj> returnList = new ArrayList<>();
		DTOObj dtoObj2;
		for (Document doc : findDocList) {
			try {
				dtoObj2 = dtoObj.getClass().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				replyDTO.addErrorMsgCode(Const.MSG_CODES.INVALID_PARAMS);
				return replyDTO;
			}
			Utils.populateDTOFromDocument(doc, dtoObj2);
			returnList.add(dtoObj2);
		}
		replyDTO.setListObj(returnList);
		replyDTO.addErrorMsgCode(Const.MSG_CODES.SUCCESS);
		return replyDTO;
	}
}
