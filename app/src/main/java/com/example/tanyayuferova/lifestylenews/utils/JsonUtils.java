package com.example.tanyayuferova.lifestylenews.utils;

import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.example.tanyayuferova.lifestylenews.entity.Article;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanya Yuferova on 12/23/2017.
 */

public class JsonUtils {

    public static final String LOG_TAG = JsonUtils.class.getName();

    /**
     * Parse json string and creates articles objects
     *
     * @param jsonString
     * @return
     */
    @Nullable
    public static List<Article> readArticlesFromJson(String jsonString) {
        if (jsonString == null || jsonString.isEmpty())
            return null;

        List<Article> result = new ArrayList<>();
        String status = null, code = null, message = null;

        JsonReader reader = new JsonReader(new StringReader(jsonString));
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "status":
                        status = reader.nextString();
                        break;
                    case "code":
                        code = reader.nextString();
                        break;
                    case "message":
                        message = reader.nextString();
                        break;
                    case "articles":
                        reader.beginArray();
                        while (reader.hasNext()) {
                            result.add(readArticle(reader));
                        }
                        reader.endArray();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if ("error".equals(status)) {
            Log.e(LOG_TAG, String.format("Error loading data. Code: %1$s. Message: %2$s", code, message));
        }

        return result;
    }


    /**
     * Reads article from json reader
     *
     * @param reader
     * @return
     */
    private static Article readArticle(JsonReader reader) {
        Article result = new Article();

        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "source":
                        if (reader.peek() == JsonToken.NULL) {
                            reader.skipValue();
                        } else {
                            reader.beginObject();
                            while (reader.hasNext()) {
                                if (reader.nextName().equals("name")) {
                                    result.setSourceName(reader.nextString());
                                } else reader.skipValue();
                            }
                            reader.endObject();
                        }
                        break;
                    case "author":
                        if (reader.peek() == JsonToken.NULL) {
                            reader.skipValue();
                        } else
                            result.setAuthor(reader.nextString());
                        break;
                    case "title":
                        if (reader.peek() == JsonToken.NULL) {
                            reader.skipValue();
                        } else
                            result.setTitle(reader.nextString());
                        break;
                    case "description":
                        if (reader.peek() == JsonToken.NULL) {
                            reader.skipValue();
                        } else
                            result.setDescription(reader.nextString());
                        break;
                    case "url":
                        if (reader.peek() == JsonToken.NULL) {
                            reader.skipValue();
                        } else
                            result.setUrl(reader.nextString());
                        break;
                    case "urlToImage":
                        if (reader.peek() == JsonToken.NULL) {
                            reader.skipValue();
                        } else
                            result.setUrlToImage(reader.nextString());
                        break;
                    case "publishedAt":
                        if (reader.peek() == JsonToken.NULL) {
                            reader.skipValue();
                        } else
                            result.setPublished(DateConverter.stringToISO8601Format(reader.nextString()));
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
