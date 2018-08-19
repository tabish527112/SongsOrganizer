package com.mtk.songsOrganizer.common.dto;

import com.mtk.songsOrganizer.common.annotations.Entity;
import com.mtk.songsOrganizer.common.annotations.IdField;
import com.mtk.songsOrganizer.common.annotations.MandatoryField;
import com.mtk.songsOrganizer.common.utils.Const;

@Entity(collectionName = Const.DB_STRUCTURE.COMMON.COLLECTIONS.COUNTRY, dbName = Const.DB_STRUCTURE.COMMON.DB_NAME)
public class CountryDTO implements DTOObj {
	@IdField(isPrimary = true)
	private int countryId;
	@MandatoryField
	private String countryName;
	private int visibility;

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

}
