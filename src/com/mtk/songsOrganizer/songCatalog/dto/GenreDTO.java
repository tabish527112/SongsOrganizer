package com.mtk.songsOrganizer.songCatalog.dto;

import java.util.List;

import com.mtk.songsOrganizer.common.annotations.Entity;
import com.mtk.songsOrganizer.common.annotations.IdField;
import com.mtk.songsOrganizer.common.annotations.IgnoreField;
import com.mtk.songsOrganizer.common.annotations.MandatoryField;
import com.mtk.songsOrganizer.common.dto.DTOObj;
import com.mtk.songsOrganizer.common.utils.Const;

@Entity(collectionName = Const.DB_STRUCTURE.SONG_CATALOG.COLLECTIONS.GENRE, dbName = Const.DB_STRUCTURE.SONG_CATALOG.DB_NAME)
public class GenreDTO implements DTOObj {
	@IdField(isPrimary = true)
	private String genreId;
	@MandatoryField
	private String genreName;
	private double rating;
	private List<GenreDTO> childGenreList;
	private String imgName;
	private int visibility;

	@IgnoreField(isParentIdField = true)
	private String parentGenreId;

	public String getGenreId() {
		return genreId;
	}

	public void setGenreId(String genreId) {
		this.genreId = genreId;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public List<GenreDTO> getChildGenreList() {
		return childGenreList;
	}

	public void setChildGenreList(List<GenreDTO> childGenreList) {
		this.childGenreList = childGenreList;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public String getParentGenreId() {
		return parentGenreId;
	}

	public void setParentGenreId(String parentGenreId) {
		this.parentGenreId = parentGenreId;
	}

}
