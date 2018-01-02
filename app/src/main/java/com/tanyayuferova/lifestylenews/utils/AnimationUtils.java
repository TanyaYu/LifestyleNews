package com.tanyayuferova.lifestylenews.utils;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.support.v7.widget.RecyclerView;
import android.view.animation.LayoutAnimationController;

/**
 * Created by Tanya Yuferova on 12/26/2017.
 */

public class AnimationUtils {

    public static void runLayoutAnimation(final RecyclerView recyclerView, @AnimRes int aminId) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                android.view.animation.AnimationUtils.loadLayoutAnimation(context, aminId);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}
