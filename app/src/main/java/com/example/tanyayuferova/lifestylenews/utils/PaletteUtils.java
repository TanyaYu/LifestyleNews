package com.example.tanyayuferova.lifestylenews.utils;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;

/**
 * Created by Tanya Yuferova on 12/19/2017.
 */

public class PaletteUtils {

    /**
     * Returns drak muted color from bitmap
     * @param bitmap
     * @param defaultColor
     * @return
     */
    public static int getDarkMutedColor(Bitmap bitmap, int defaultColor) {
        return createPaletteSync(bitmap).getDarkMutedColor(defaultColor);
    }

    /**
     * Generate palette synchronously and return it
     * @param bitmap
     * @return
     */
    private static Palette createPaletteSync(Bitmap bitmap) {
        return Palette.from(bitmap).generate();
    }
}
