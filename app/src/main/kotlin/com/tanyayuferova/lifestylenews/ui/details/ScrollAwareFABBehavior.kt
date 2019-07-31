package com.tanyayuferova.lifestylenews.ui.details

/**
 * Author: Tanya Yuferova
 * Date: 7/28/2019
 */

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Hides FAB when scrolling down
 *
 * Created by Tanya Yuferova on 12/20/2017.
 */

class ScrollAwareFABBehavior(context: Context, attrs: AttributeSet) : FloatingActionButton.Behavior() {

    private var animationDuration: Long = 400

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)

        //When scroll down - hide, when scroll up - show
        if (dyConsumed > 0 && child.getVisibility() === View.VISIBLE) {
            val animation = AlphaAnimation(1.0f, 0.0f)
            animation.duration = animationDuration
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {

                }

                override fun onAnimationEnd(animation: Animation) {
                    child.setVisibility(View.INVISIBLE)
                    child.setAlpha(0.0f)
                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            })
            child.startAnimation(animation)

            // This code doesn't work because it seems that an android animation is not truly finished when the onAnimationEnd event is fired
            //            child.animate()
            //                    .alpha(0.0f)
            //                    .setDuration(animationDuration)
            //                    .setListener(new AnimatorListenerAdapter() {
            //                        @Override
            //                        public void onAnimationEnd(Animator animation) {
            //                            super.onAnimationEnd(animation);
            //                            child.clearAnimation();
            //                            //FixMe doesn't work
            //                            //child.setVisibility(View.INVISIBLE);
            //                        }
            //                    }).start();

        } else if (dyConsumed < 0 && child.getVisibility() !== View.VISIBLE) {
            child.setVisibility(View.VISIBLE)
            child.animate()
                .alpha(1.0f)
                .setDuration(animationDuration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        child.clearAnimation()
                    }
                }).start()
        }
    }

    override fun onStartNestedScroll(
         coordinatorLayout: CoordinatorLayout,  child: FloatingActionButton,  directTargetChild: View,
         target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }
}

