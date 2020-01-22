package com.lmy.hwahae.ui.utils

import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

object AnimationHelper {
    fun getSpringAnimation(view: View, startValue: Float, springForce: SpringForce) : SpringAnimation {
        return SpringAnimation(view, DynamicAnimation.TRANSLATION_Y)
            .setSpring(springForce)
            .setStartValue(startValue)
    }

    fun getSpringForce(height: Float, dampingRatio: Float, stiffness: Float): SpringForce {
        return SpringForce(height)
            .setDampingRatio(dampingRatio)
            .setStiffness(stiffness)
    }
}