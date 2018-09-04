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

class SplashView(context: Context, attrs: AttributeSet?) :
    View(context, attrs) {

    private val totalWidthPx = context.resources?.displayMetrics?.widthPixels!!
    private val totalHeightPx = context.resources?.displayMetrics?.heightPixels!!

    private val canvasBitmap =
        Bitmap.createBitmap(totalWidthPx, totalHeightPx, Bitmap.Config.ARGB_8888)

    init {
        val paint = Paint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        }

        val logoDrawable = context.getDrawable(R.mipmap.logo)!!
        logoDrawable.setTint(Color.TRANSPARENT)
        logoDrawable.setTintMode(PorterDuff.Mode.DST_OUT)
        val logoBitmap = logoDrawable.toBitmap()

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
