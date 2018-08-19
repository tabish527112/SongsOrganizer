package com.mtk.songsOrganizer.common.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParamDTO implements Serializable {
	private DTOObj dtoObj;
	private List listObj;
	private int pageNo;
	private int numRecords;
	private Map otherInfo;
	private Set<String> fieldSet;

	public ParamDTO() {

	}

	public ParamDTO(DTOObj dtoObj) {
		this.dtoObj = dtoObj;
	}

	public ParamDTO(List listObj) {
		this.listObj = listObj;
	}

	public ParamDTO(DTOObj dtoObj, List listObj) {
		this.dtoObj = dtoObj;
		this.listObj = listObj;
	}

	public ParamDTO(DTOObj dtoObj, List listObj, Set<String> fieldSet) {
		this.dtoObj = dtoObj;
		this.listObj = listObj;
		this.fieldSet = fieldSet;
	}

	public ParamDTO(DTOObj dtoObj, List listObj, int pageNo, int numRecords) {
		this.dtoObj = dtoObj;
		this.listObj = listObj;
		this.pageNo = pageNo;
		this.numRecords = numRecords;
	}

	public List getListObj() {
		return listObj;
	}

	public void setListObj(List listObj) {
		this.listObj = listObj;
	}

	public DTOObj getDtoObj() {
		return dtoObj;
	}

	public void setDtoObj(DTOObj dtoObj) {
		this.dtoObj = dtoObj;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getNumRecords() {
		return numRecords;
	}

	public void setNumRecords(int numRecords) {
		this.numRecords = numRecords;
	}

	public Map getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(Map otherInfo) {
		this.otherInfo = otherInfo;
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

	public Set<String> getFieldSet() {
		return fieldSet;
	}

	public void setFieldSet(Set<String> fieldSet) {
		this.fieldSet = fieldSet;
	}

}
