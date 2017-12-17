package com.example.tanyayuferova.lifestylenews.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Tanya Yuferova on 11/10/2017.
 */

public class BindingAdaptersUtils {

    @BindingAdapter({"bind:imageUrl", "bind:error"})
    public static void loadImage(ImageView view, String url, Drawable error) {
        Picasso.with(view.getContext())
                .load(url)
                .placeholder(error)
                .error(error)
                .into(view);
    }
}
