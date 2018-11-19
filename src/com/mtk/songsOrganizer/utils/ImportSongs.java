package com.mtk.songsOrganizer.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.mtk.songsOrganizer.common.utils.Utils;

public class ImportSongs {
	static Set<String> allowedExtensionSet, skipFolderSet;
	static {
		allowedExtensionSet = new HashSet<>();
		allowedExtensionSet.add("mp3");
		allowedExtensionSet.add("wma");
		allowedExtensionSet.add("flac");

		skipFolderSet = new HashSet<>();
		skipFolderSet.add("bollywood");
		skipFolderSet.add("others");
	}

	public static void main(String[] args) {
		new ImportSongs().importSongs("/home/mtk/Music");
	}

	public boolean importSongs(String basePath) {
		List<String> songList = new ArrayList<>();
		findSongs(new File(basePath), songList, basePath);
		for (String s : songList) {
			System.out.println(s);
		}
		return true;
	}

	public void findSongs(File baseFile, List<String> songList, String basePath) {
		if (baseFile == null || !baseFile.exists() || !baseFile.isDirectory()) {
			return;
		}
		if (songList == null) {
			songList = new ArrayList<>();
		}

		Set<File> fileSet = new TreeSet<>(Arrays.asList(baseFile.listFiles()));
		for (File file : fileSet) {
			if (file.isDirectory()) {
				if (skipFolderSet.contains(file.getName().toLowerCase())) {
					continue;
				}
				findSongs(file, songList, basePath);
			}
		}

		String[] arr;
		for (File file : fileSet) {
			if (!file.isDirectory()) {
				arr = file.getName().split("[.]");
				if (arr.length == 0 || !allowedExtensionSet.contains(arr[arr.length - 1].toLowerCase())) {
					continue;
				}

				if (Utils.isEmpty(basePath)) {
					songList.add(file.getAbsolutePath());
				} else {
					songList.add(file.getAbsolutePath().replace(basePath, ""));
				}
			}
		}
	}
}
