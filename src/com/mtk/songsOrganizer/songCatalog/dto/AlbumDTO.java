package com.mtk.songsOrganizer.songCatalog.dto;

import java.util.List;

import com.mtk.songsOrganizer.common.annotations.Entity;
import com.mtk.songsOrganizer.common.annotations.IdField;
import com.mtk.songsOrganizer.common.annotations.MandatoryField;
import com.mtk.songsOrganizer.common.dto.DTOObj;
import com.mtk.songsOrganizer.common.utils.Const;

@Entity(collectionName = Const.DB_STRUCTURE.SONG_CATALOG.COLLECTIONS.ALBUM, dbName = Const.DB_STRUCTURE.SONG_CATALOG.DB_NAME)
public class AlbumDTO implements DTOObj {
	@IdField(isPrimary = true)
	private long albumId;
	@MandatoryField
	private String albumName;
	private List<Long> artistIdList;
	private double rating;
	private String albumArtName;
	private int countryId;
	private int visibility;

	public long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(long albumId) {
		this.albumId = albumId;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public List<Long> getArtistIdList() {
		return artistIdList;
	}

	public void setArtistIdList(List<Long> artistIdList) {
		this.artistIdList = artistIdList;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getAlbumArtName() {
		return albumArtName;
	}

	public void setAlbumArtName(String albumArtName) {
		this.albumArtName = albumArtName;
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
