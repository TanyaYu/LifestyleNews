package com.tanyayuferova.lifestylenews.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.Date;

/**
 * Article entity
 * Created by Tanya Yuferova on 12/16/2017.
 */

public class Article implements Parcelable {

    private int id;
    private String sourceName;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private Date published;
    private boolean favorite = false;
    private Date addedToFavored;

    public Article() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        if(TextUtils.isEmpty(urlToImage))
            return null;
        // Some images do not have http: in their url
        if(!(urlToImage.startsWith("https:") || urlToImage.startsWith("http:")))
            urlToImage = "https:" + urlToImage;
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Date getAddedToFavored() {
        return addedToFavored;
    }

    public void setAddedToFavored(Date addedToFavored) {
        this.addedToFavored = addedToFavored;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeByte((byte) (favorite ? 1 : 0));

        String[] strings = new String[6];
        strings[0] = sourceName;
        strings[1] = author;
        strings[2] = title;
        strings[3] = description;
        strings[4] = url;
        strings[5] = urlToImage;
        parcel.writeStringArray(strings);

        long[] longs = new long[2];
        longs[0] = published == null ? 0 : published.getTime();
        longs[1] = addedToFavored == null ? 0 : addedToFavored.getTime();
        parcel.writeLongArray(longs);
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    private Article(Parcel parcel) {
        id = parcel.readInt();
        favorite = parcel.readByte() == 1;

        String[] strings = new String[6];
        parcel.readStringArray(strings);
        sourceName = strings[0];
        author = strings[1];
        title = strings[2];
        description = strings[3];
        url = strings[4];
        urlToImage = strings[5];

        long[] longs = new long[2];
        parcel.readLongArray(longs);
        if(longs[0] > 0) published = new Date(longs[0]);
        if(longs[1] > 0) addedToFavored = new Date(longs[1]);
    }

    @Override
    public String toString() {
        return getTitle();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Article)
            return ((Article) obj).getId() == getId();
        return super.equals(obj);
    }
}
