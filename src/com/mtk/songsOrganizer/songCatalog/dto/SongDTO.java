package com.mtk.songsOrganizer.songCatalog.dto;

import java.util.Date;
import java.util.List;

import com.mtk.songsOrganizer.common.annotations.Entity;
import com.mtk.songsOrganizer.common.annotations.IdField;
import com.mtk.songsOrganizer.common.annotations.MandatoryField;
import com.mtk.songsOrganizer.common.dto.DTOObj;
import com.mtk.songsOrganizer.common.utils.Const;

@Entity(collectionName = Const.DB_STRUCTURE.SONG_CATALOG.COLLECTIONS.SONG, dbName = Const.DB_STRUCTURE.SONG_CATALOG.DB_NAME)
public class SongDTO implements DTOObj {
	@IdField(isPrimary = true)
	private long songId;
	private String genreId;
	private List<Long> artistIdList;
	private List<Long> albumArtistIdList;
	private List<Long> composerIdList;
	private long albumId;
	@MandatoryField
	private String title;
	private double rating;
	private int trackNo; // in album
	private int discNo; // in album
	private String year;
	private int bitrate;
	private String filePath;
	private String fileName;
	private double fileSize;
	private String comment;
	private long playCount;
	private Date lastPlayed;
	private Date dateCreated;
	private String albumArtName;
	private int visibility;

	public long getSongId() {
		return songId;
	}

	public void setSongId(long songId) {
		this.songId = songId;
	}

	public String getGenreId() {
		return genreId;
	}

	public void setGenreId(String genreId) {
		this.genreId = genreId;
	}

	public List<Long> getArtistIdList() {
		return artistIdList;
	}

	public void setArtistIdList(List<Long> artistIdList) {
		this.artistIdList = artistIdList;
	}

	public List<Long> getAlbumArtistIdList() {
		return albumArtistIdList;
	}

	public void setAlbumArtistIdList(List<Long> albumArtistIdList) {
		this.albumArtistIdList = albumArtistIdList;
	}

	public List<Long> getComposerIdList() {
		return composerIdList;
	}

	public void setComposerIdList(List<Long> composerIdList) {
		this.composerIdList = composerIdList;
	}

	public long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(long albumId) {
		this.albumId = albumId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getTrackNo() {
		return trackNo;
	}

	public void setTrackNo(int trackNo) {
		this.trackNo = trackNo;
	}

	public int getDiscNo() {
		return discNo;
	}

	public void setDiscNo(int discNo) {
		this.discNo = discNo;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getBitrate() {
		return bitrate;
	}

	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public double getFileSize() {
		return fileSize;
	}

	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public long getPlayCount() {
		return playCount;
	}

	public void setPlayCount(long playCount) {
		this.playCount = playCount;
	}

	public Date getLastPlayed() {
		return lastPlayed;
	}

	public void setLastPlayed(Date lastPlayed) {
		this.lastPlayed = lastPlayed;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getAlbumArtName() {
		return albumArtName;
	}

	public void setAlbumArtName(String albumArtName) {
		this.albumArtName = albumArtName;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

}
