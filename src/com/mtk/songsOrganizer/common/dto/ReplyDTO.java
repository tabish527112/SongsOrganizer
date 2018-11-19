package com.mtk.songsOrganizer.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReplyDTO implements Serializable {
	private DTOObj dtoObj;
	private List listObj;
	private Set<String> msgList;
	private Map otherInfo;
	private long count;
	private boolean lastPage;
	private boolean error;
	private int msgCode;

	public ReplyDTO() {

	}

	public ReplyDTO(List listObj) {
		this.listObj = listObj;
	}

	public ReplyDTO(DTOObj dtoObj) {
		this.dtoObj = dtoObj;
	}

	public DTOObj getDtoObj() {
		return dtoObj;
	}

	public void setDtoObj(DTOObj dtoObj) {
		this.dtoObj = dtoObj;
	}

	public void addOtherInfo(Object key, Object value) {
		if (this.otherInfo == null) {
			this.otherInfo = new HashMap<>();
		}
		this.otherInfo.put(key, value);
	}

	public Object getOtherInfo(Object key) {
		if (this.otherInfo == null) {
			return null;
		}
		return this.otherInfo.get(key);
	}

	public Map getOtherInfoMap() {
		return this.otherInfo;
	}

	public void setOtherInfoMap(Map infoMap) {
		this.otherInfo = infoMap;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List getListObj() {
		return listObj;
	}

	public void setListObj(List listObj) {
		this.listObj = listObj;
	}

	public void addToListObj(Object val) {
		if (listObj == null) {
			listObj = new ArrayList<>();
		}
		listObj.add(val);
	}

	public Set<String> getMsgList() {
		return msgList;
	}

	public void setMsgList(Set<String> msgList) {
		this.msgList = msgList;
	}

	public void addInfoMsg(String msg) {
		if (msgList == null) {
			msgList = new HashSet<>();
		}
		msgList.add(msg);
	}

	public void addErrorMsg(String msg) {
		error = true;
		addInfoMsg(msg);
	}

	public Map getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(Map otherInfo) {
		this.otherInfo = otherInfo;
	}

	public void addOtherInfo(String key, Object value) {
		if (otherInfo == null) {
			otherInfo = new HashMap<>();
		}
		otherInfo.put(key, value);
	}

	public boolean hasError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public int getMsgCode() {
		return msgCode;
	}

	public void addMsgCode(int msgCode) {
		this.msgCode = msgCode;
	}

	public void addErrorMsgCode(int msgCode) {
		error = true;
		addMsgCode(msgCode);
	}
}
