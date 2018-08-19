package com.mtk.songsOrganizer.songCatalog.dto;

import com.mtk.songsOrganizer.common.annotations.Entity;
import com.mtk.songsOrganizer.common.annotations.IdField;
import com.mtk.songsOrganizer.common.annotations.MandatoryField;
import com.mtk.songsOrganizer.common.dto.DTOObj;
import com.mtk.songsOrganizer.common.utils.Const;

@Entity(collectionName = Const.DB_STRUCTURE.SONG_CATALOG.COLLECTIONS.ARTIST, dbName = Const.DB_STRUCTURE.SONG_CATALOG.DB_NAME)
public class ArtistDTO implements DTOObj {
	@IdField(isPrimary = true)
	private long artistId;
	@MandatoryField
	private String artistName;
	private double rating;
	private String imgName;
	private int countryId;
	private int visibility;

	public long getArtistId() {
		return artistId;
	}

	public void setArtistId(long artistId) {
		this.artistId = artistId;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

}
