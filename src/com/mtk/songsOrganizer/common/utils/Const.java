package com.mtk.songsOrganizer.common.utils;

public class Const {
	public interface MSG_CODES {
		final int INVALID_PARAMS = 1;
		final int NO_ID_FIELD = 2;
		final int ADDED = 3;
		final int UPDATED = 4;
		final int NO_DATA_FOUND = 5;
	}

	public interface LAYOUT_CONST {
		final String ID = "id";
	}

	public interface VISIBILITY {
		final int VISIBLE = 0;
		final int HIDDEN = 1;
		final int DELETE = 2;
	}

	public interface DB_STRUCTURE {
		interface COMMON {
			final String DB_NAME = "common";

			interface COLLECTIONS {
				final String COUNTRY = "country";
			}
		}

		interface USER {
			final String DB_NAME = "user";

			interface COLLECTIONS {
				final String PLAYLIST = "playList";
				final String USER = "user";
			}
		}

		interface SONG_CATALOG {
			final String DB_NAME = "songCatalog";

			interface COLLECTIONS {
				final String ALBUM = "album";
				final String ARTIST = "artist";
				final String GENRE = "genre";
				final String SONG = "song";
			}
		}
	}
}
