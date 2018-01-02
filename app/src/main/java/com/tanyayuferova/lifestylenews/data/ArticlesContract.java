package com.tanyayuferova.lifestylenews.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Tanya Yuferova on 12/22/2017.
 */

public class ArticlesContract {

    public static final String CONTENT_AUTHORITY = "com.tanyayuferova.lifestylenews";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_RECENT = "recent";
    public static final String PATH_FAVORITE = "favorite";

    public static final Uri CONTENT_RECENT_URI = BASE_CONTENT_URI.buildUpon()
            .appendPath(PATH_RECENT)
            .build();

    public static final Uri CONTENT_FAVORITE_URI = BASE_CONTENT_URI.buildUpon()
            .appendPath(PATH_FAVORITE)
            .build();

    public static final class ArticleEntry implements BaseColumns {
        public static final String TABLE_NAME = "article";

        public static final String COLUMN_SOURCE_NAME = "source_name";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_URL_TO_IMAGE = "url_to_image";
        public static final String COLUMN_PUBLISHED_AT = "published_at";
        public static final String COLUMN_FAVORITE = "favorite";
        public static final String COLUMN_FAVORITE_DATE = "favorite_date";
    }
}
