package com.mtk.songsOrganizer.user.dto;

import java.util.Date;
import java.util.List;

import com.mtk.songsOrganizer.common.annotations.Entity;
import com.mtk.songsOrganizer.common.annotations.IdField;
import com.mtk.songsOrganizer.common.annotations.MandatoryField;
import com.mtk.songsOrganizer.common.dto.DTOObj;
import com.mtk.songsOrganizer.common.utils.Const;

@Entity(collectionName = Const.DB_STRUCTURE.USER.COLLECTIONS.PLAYLIST, dbName = Const.DB_STRUCTURE.USER.DB_NAME)
public class PlayListDTO implements DTOObj {
	@IdField(isPrimary = false)
	private long userId;
	@IdField(isPrimary = true)
	private long playlistId;
	@MandatoryField
	private String playlistName;
	private List<PlayListItemDTO> itemList;
	private int type; // auto-gen / manual
	private Date dateCreated;
	private Date dateModified;
	private int visibility;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(long playlistId) {
		this.playlistId = playlistId;
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	public List<PlayListItemDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<PlayListItemDTO> itemList) {
		this.itemList = itemList;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

}
