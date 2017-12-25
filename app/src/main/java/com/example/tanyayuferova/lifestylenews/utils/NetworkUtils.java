package com.example.tanyayuferova.lifestylenews.utils;

import android.content.Context;
import android.net.Uri;

import com.example.tanyayuferova.lifestylenews.entity.Article;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static com.example.tanyayuferova.lifestylenews.utils.PreferencesUtils.getTopicsPreferences;

/**
 * Created by Tanya Yuferova on 12/23/2017.
 */

public class NetworkUtils {

    private static final String BASE_URL = "https://newsapi.org/";
    private static final String VERSION = "v2";
    private static final String ENDPOINT = "everything";
    private static final String QUERY_PARAM = "q";
    private static final String API_KEY_PARAM = "apiKey";
    private static final String API_KEY = "e0c55009fdd64c3787c348c699da58b0"; //FIXME API_KEY
    private static final String SORT_PARAM = "sortBy";
    private static final String SORT_PARAM_DEFAULT = "relevance";
    private static final String PAGE_PARAM = "page";
    private static final String LANGUAGE_PARAM = "language";
    private static final String LANGUAGE_DEFAULT = "en";
    private static final String FROM_DATE_PARAM = "from";
    private static final String TO_DATE_PARAM = "to";
    private static final String QUERY_VALUES_DIVIDER = " OR ";

    /**
     * Loads articles from News API for topics in preferences
     *
     * @param context
     * @param fromDate
     * @param countLimit
     * @return
     */
    public static List<Article> loadArticles(Context context, Date fromDate, Date toDate, int countLimit) {
        List<Article> result = new ArrayList<>();
        String query = getTopicsPreferencesStringQuery(context);
        if (query == null || query.isEmpty())
            return null;

        int page = 1;
        int count = 0;

        // download article while we get needed amount
        while (count < countLimit) {
            String json = getJsonData(
                    buildArticlesUrl(query,
                            DateConverter.dateToISO8601Format(fromDate),
                            DateConverter.dateToISO8601Format(toDate),
                            page++));
            List<Article> data = JsonUtils.readArticlesFromJson(json);

            //something went wrong, return null
            if(data == null)
                return null;

            //no more data available, return what we have
            if(data.size() == 0)
                return result;

            // add articles in result while we get as many as we need
            int index = 0;
            while (count < countLimit && index < data.size()) {
                result.add(data.get(index++));
                count++;
            }
        }

        return result;
    }

    /**
     * Builds url for News API to get articles list
     *
     * @param pageNumber
     * @return
     */
    public static URL buildArticlesUrl(String query, String fromDate, String toDate, int pageNumber) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(VERSION)
                .appendPath(ENDPOINT)
                .appendQueryParameter(QUERY_PARAM, query)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .appendQueryParameter(SORT_PARAM, SORT_PARAM_DEFAULT)
                .appendQueryParameter(LANGUAGE_PARAM, LANGUAGE_DEFAULT)
                .appendQueryParameter(PAGE_PARAM, String.valueOf(pageNumber))
                .appendQueryParameter(FROM_DATE_PARAM, fromDate)
                .appendQueryParameter(TO_DATE_PARAM, toDate)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Gets response from url
     *
     * @param url
     * @return
     */
    public static String getJsonData(URL url) {
        String result = null;
        try {
            result = getResponseFromHttpUrl(url);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * Gets topics preferences in format topic1 OR topic2 OR topic 3
     *
     * @param context
     * @return
     */
    private static String getTopicsPreferencesStringQuery(Context context) {
        Set<String> topics = getTopicsPreferences(context);
        String result = "";
        for (String s : topics) {
            result += QUERY_VALUES_DIVIDER + s;
        }
        if (result.length() >= QUERY_VALUES_DIVIDER.length()) {
            result = result.substring(QUERY_VALUES_DIVIDER.length());
        }
        return result;
    }
}
