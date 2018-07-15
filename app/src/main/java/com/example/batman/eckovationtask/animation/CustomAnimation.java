package com.example.batman.eckovationtask.animation;

import android.animation.Animator;
import android.os.Build;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewPropertyAnimator;

import static android.support.animation.SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY;
import static android.support.animation.SpringForce.STIFFNESS_LOW;

public class CustomAnimation {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void circularRevealAnimate(View view) {

        int centerX = view.getWidth() / 2;
        int centerY = view.getHeight() / 2;
        float endRadius = (float) centerX;
        Animator animator = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, 0, endRadius);
        animator.start();
    }

    public static ViewPropertyAnimator getFabAnimator(View view, float rotation, long duration) {
        ViewPropertyAnimator animator = view.animate();
        animator.rotation(rotation);
        animator.setDuration(duration);
        return animator;
    }

    public static ViewPropertyAnimator getJoinGroupAnimator(View view, boolean isExpanded, float alpha, long duration) {
        ViewPropertyAnimator animator = view.animate()
                .alpha(alpha)
                .setDuration(duration);

        if (isExpanded) {
            animator.translationY(0f);
        }
        return animator;
    }

    public static void springAnimateFab(View view) {
        SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.TRANSLATION_Y, -220f);
        springAnimation.getSpring().setDampingRatio(DAMPING_RATIO_MEDIUM_BOUNCY);
        springAnimation.getSpring().setStiffness(STIFFNESS_LOW);
        springAnimation.start();
    }

    public static void getAddChilsAnimate(View view) {
        view.animate()
                .alpha(1f)
                .setDuration(400);
    }

}
