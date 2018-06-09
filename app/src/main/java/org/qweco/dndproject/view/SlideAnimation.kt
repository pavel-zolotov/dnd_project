package org.qweco.dndproject.view

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation


class SlideAnimation (
    var mFromHeight: Int = 0,
    var mToHeight: Int = 0,
    var mView: View): Animation() {


    override fun applyTransformation(interpolatedTime: Float, transformation: Transformation) {
        val newHeight: Int

        if (mView.height != mToHeight) {
            newHeight = (mFromHeight + (mToHeight - mFromHeight) * interpolatedTime).toInt()
            mView.layoutParams.height = newHeight
            mView.requestLayout()
        }
    }

    override fun willChangeBounds(): Boolean {
        return false
    }
}