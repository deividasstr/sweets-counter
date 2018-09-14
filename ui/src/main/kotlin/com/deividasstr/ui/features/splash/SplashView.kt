package com.deividasstr.ui.features.splash

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.deividasstr.ui.R

/**
 * Dynamic splash view - for animation on MainActivity start
 */
class SplashView(context: Context, attrs: AttributeSet?) :
    View(context, attrs) {

    private var canvasBitmap: Bitmap

    init {
        val totalWidthPx = context.resources?.displayMetrics?.widthPixels!!
        val totalHeightPx = context.resources?.displayMetrics?.heightPixels!!

        val paint = Paint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        }

        val logoDrawable = context.getDrawable(R.mipmap.logo)!!.apply {
            setTint(Color.TRANSPARENT)
            setTintMode(PorterDuff.Mode.DST_OUT)
        }

        val logoBitmap = logoDrawable.toBitmap()

        canvasBitmap = Bitmap.createBitmap(totalWidthPx, totalHeightPx, Bitmap.Config.ARGB_8888)

        val tempCanvas = Canvas(canvasBitmap)
        tempCanvas.drawColor(ContextCompat.getColor(context, R.color.colorPrimary))

        val centreX = (totalWidthPx - logoBitmap.width) / 2f
        val centreY = (totalHeightPx - logoBitmap.height) / 2f

        tempCanvas.drawBitmap(logoBitmap, centreX, centreY, paint)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawBitmap(canvasBitmap, 0f, 0f, null)
    }
}
