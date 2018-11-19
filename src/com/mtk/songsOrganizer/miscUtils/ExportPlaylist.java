package com.mtk.songsOrganizer.miscUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.mtk.songsOrganizer.common.utils.Utils;

public class ExportPlaylist {
	public static final String RHYTHMBOX_PLAYLIST_XML_PATH = "/home/mtk/.local/share/rhythmbox/";
	public static final String DEFAULT_FIX_PATH = "/home/mtk/Music/";
	public static final String DEFAULT_OUT_DIR = "/home/mtk/dump/PlaylistDump/";
	public static final String DEFAULT_PLAYLIST_NAME = "MTK";

	interface RHYTHMBOX_XML_CONSTS {
		static String XML_FILE_NAME = "playlists.xml";
		static String ROOT_ELE = "rhythmdb-playlists";
		static String PLAYLIST_NAME = "name";
		static String FILE_PREFIX = "file://";
	}

	private static Element getPlaylistElement(String pathXml, String playlistName) {
		File inputFile = new File(pathXml + RHYTHMBOX_XML_CONSTS.XML_FILE_NAME);
		if (!inputFile.exists()) {
			System.out.println("playlists.xml file doesnt exist");
			return null;
		}
		Document document;
		try {
			SAXReader reader = new SAXReader();
			document = reader.read(inputFile);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		if (document.getRootElement() == null
				|| !document.getRootElement().getName().equals(RHYTHMBOX_XML_CONSTS.ROOT_ELE)) {
			System.out.println("Structure improper");
			return null;
		}

		Element plEle = null;
		for (Element element : (List<Element>) document.getRootElement().elements()) {
			if (element.attributeValue(RHYTHMBOX_XML_CONSTS.PLAYLIST_NAME).equals(playlistName)) {
				plEle = element;
			}
		}

		if (plEle == null) {
			System.out.println("Playlist not found");
			return null;
		}

		return plEle;
	}

	public static boolean exportRhythmboxPlaylist(String pathXml, String fixPath, String outDir, String playlistName)
			throws IOException {
		pathXml = Utils.isEmpty(pathXml) ? RHYTHMBOX_PLAYLIST_XML_PATH : pathXml;
		fixPath = Utils.isEmpty(fixPath) ? DEFAULT_FIX_PATH : fixPath;
		outDir = Utils.isEmpty(outDir) ? DEFAULT_OUT_DIR : outDir;
		playlistName = Utils.isEmpty(playlistName) ? DEFAULT_PLAYLIST_NAME : playlistName;

		String data;
		File fileSrc, fileDest;

		Element plEleCurr = getPlaylistElement(outDir, playlistName);
		Set<String> existingSongSet = new HashSet<>();
		if (plEleCurr != null) {
			System.out.println("Existing playlist found");
			for (Element element : (List<Element>) plEleCurr.elements()) {
				data = (String) element.getData();
				if (!Utils.isEmpty(data) && data.startsWith(RHYTHMBOX_XML_CONSTS.FILE_PREFIX)) {
					existingSongSet.add(data.replace(RHYTHMBOX_XML_CONSTS.FILE_PREFIX, ""));
				}
			}
		}

		Element plEle = getPlaylistElement(pathXml, playlistName);
		if (plEle == null) {
			return false;
		}

		int i = 0, total = plEle.elements().size();
		boolean isFirst = true;
		String dataDecoded;
		for (Element element : (List<Element>) plEle.elements()) {
			data = (String) element.getData();
			if (!Utils.isEmpty(data) && data.startsWith(RHYTHMBOX_XML_CONSTS.FILE_PREFIX)) {
				data = data.replace(RHYTHMBOX_XML_CONSTS.FILE_PREFIX, "");
				dataDecoded = java.net.URLDecoder.decode(data, "UTF-8");
				fileDest = new File(outDir + dataDecoded.replace(fixPath, ""));
				if (existingSongSet.contains(data) && fileDest.exists()) {
					existingSongSet.remove(data);
					continue;
				}
				existingSongSet.remove(data);
				if (isFirst) {
					System.out.println("\nAdding songs\n-----------------------------------------");
					isFirst = false;
				}
				fileSrc = new File(dataDecoded);
				FileUtils.copyFile(fileSrc, fileDest);
				System.out.println(++i + "/" + total + " : " + fileDest.getName());
			}
		}

		File path;
		int removeCount = existingSongSet.size();
		if (removeCount > 0) {
			System.out.println("\nRemoving songs\n-----------------------------------------");
			int j = 1;
			for (String data1 : existingSongSet) {
				data1 = java.net.URLDecoder.decode(data1, "UTF-8");
				fileDest = new File(outDir + data1.replace(fixPath, ""));
				fileDest.delete();
				System.out.println(j + "/" + removeCount + " : " + fileDest.getName());

				// delete empty folders
				path = fileDest.getParentFile();
				while (path.isDirectory() && path.exists() && path.list().length == 0) {
					path.delete();
					path = path.getParentFile();
				}
				j++;
			}
		}

		FileUtils.copyFile(new File(pathXml + RHYTHMBOX_XML_CONSTS.XML_FILE_NAME),
				new File(outDir + RHYTHMBOX_XML_CONSTS.XML_FILE_NAME));

		System.out.println("\n-----------------------------------------\nTotal: " + total + " ; Added: " + i
				+ " ; Removed: " + removeCount);
		return true;
	}

	public static void main(String[] args) throws IOException {
		System.out.println(
				"-----------------------------------------\nPlaylist Sync Utility\n-----------------------------------------");
		String pathXml = null, fixPath = null, outDir = null, playlistName = null;
		if (args != null) {
			pathXml = args.length > 0 && !Utils.isEmpty(args[0]) ? args[0] : RHYTHMBOX_PLAYLIST_XML_PATH;
			fixPath = args.length > 1 && !Utils.isEmpty(args[1]) ? args[1] : DEFAULT_FIX_PATH;
			outDir = args.length > 2 && !Utils.isEmpty(args[2]) ? args[2] : DEFAULT_OUT_DIR;
			playlistName = args.length > 3 && !Utils.isEmpty(args[3]) ? args[3] : DEFAULT_PLAYLIST_NAME;
		}
		if (exportRhythmboxPlaylist(pathXml, fixPath, outDir, playlistName)) {
			System.out.println("Terminated successfully");
		} else {
			System.out.println("Terminated with error");
		}
	}
}
