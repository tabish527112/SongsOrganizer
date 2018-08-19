package com.mtk.songsOrganizer.user.dto;

import java.util.Date;

import com.mtk.songsOrganizer.common.dto.DTOObj;

public class PlayListItemDTO implements DTOObj {
	private long songId;
	private Date dateAdded;
	private int index;
	private long numTimesPlayed;
	private int visibility;

	public long getSongId() {
		return songId;
	}

	public void setSongId(long songId) {
		this.songId = songId;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public long getNumTimesPlayed() {
		return numTimesPlayed;
	}

	public void setNumTimesPlayed(long numTimesPlayed) {
		this.numTimesPlayed = numTimesPlayed;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

}
