package com.tanyayuferova.lifestylenews.utils;

import com.tanyayuferova.lifestylenews.entity.Article;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanya Yuferova on 12/16/2017.
 */

public class TestUtils {

    public static List<Article> getTestData(int count) {
        List<Article> result = new ArrayList<>();
        for(int i = 0; i<count; i++)
            result.add(getTestArticle());
        return result;
    }

    public static Article getTestArticle() {
        Article a = new Article();
        a.setSourceName("Kinja.com");
        a.setAuthor("Nick Douglas");
        a.setTitle("Blind-Assistance App \"Be My Eyes\" Is Now on Android");
        a.setDescription("Image via Be My Eyes Be My Eyes, an app that lets sighted people remotely help blind people with visual tasks, is now available on Android, after two years on iOS. The app is free, anonymous, and available 24/7. Anyone can join as a volunteer or end user. The…");
        a.setUrl("http://feeds.kinja.com/~r/lifehacker/vip/~3/83Vao8JOX1s/blind-assistance-app-be-my-eyes-is-now-on-android-1819152831");
        a.setUrlToImage("https://i.kinja-img.com/gawker-media/image/upload/iky9fp7hv7oxr95ojeot.png");
        try {
            a.setPublished(new SimpleDateFormat("yyyy-MM-dd").parse("2017-10-05"));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return a;
    }
}
