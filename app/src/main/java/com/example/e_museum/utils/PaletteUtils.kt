package com.example.e_museum.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.palette.graphics.Palette

class PaletteUtils {
    private fun getUpperSideDominantColor(bitmap: Bitmap): Int {
        val builder = Palette.Builder(bitmap)
            .setRegion(0, 0, bitmap.width, bitmap.height / 2)
        val defaultValue = 0xFFFFFF
        val p = builder.generate()
        return p.getDominantColor(defaultValue)
    }

    private fun getLowerSideDominantColor(bitmap: Bitmap): Int {
        val defaultValue = 0xFFFFFF
        val builder = Palette.Builder(bitmap)
            .setRegion(0, bitmap.height / 2, bitmap.width, bitmap.height)
        return builder.generate().getDominantColor(defaultValue)
    }

    fun getDominantGradient(
        bitmap: Bitmap,
        cornerRadius: Float? = null,
        orientation: GradientDrawable.Orientation? = null,
        endColor: String? = null
    ): GradientDrawable {
        val topColor = getUpperSideDominantColor(bitmap)
        var topHex = String.format("#%06X", 0xFFFFFF and topColor)

        val y = 0.2126 * (hexToRgb(topHex)?.first ?: 0)
        +0.7152 * (hexToRgb(topHex)?.second ?: 0) +
                0.0722 * (hexToRgb(topHex)?.third ?: 0)


        var endHex = endColor?.let { String.format("#%06X", 0xFFFFFF and Color.parseColor(it)) }
            ?: String.format("#%06X", 0xFFFFFF and getLowerSideDominantColor(bitmap))

        if (y > 40) {
            topHex = String.format("#%06X", 0xFFFFFF and Color.DKGRAY)
            if (endColor == null) {
                endHex = String.format("#%06X", 0xFFFFFF and Color.DKGRAY)
            }
        }

        val colors = intArrayOf(Color.parseColor(topHex), Color.parseColor(endHex))
        val actualOrientation = orientation ?: GradientDrawable.Orientation.TOP_BOTTOM

        val gradientDrawable = GradientDrawable(actualOrientation, colors)

        cornerRadius?.let {
            gradientDrawable.cornerRadius = convertDpToPixels(it).toFloat()
        }

        return gradientDrawable
    }

    fun getDeeperDrawable(gradientDrawable: GradientDrawable): GradientDrawable {
        val existingGradientDrawable = gradientDrawable.mutate() as GradientDrawable
        val colors = existingGradientDrawable.colors

        if (colors != null) {
            for (i in colors.indices) {
                val color = colors[i]
                colors[i] = Color.argb(
                    Color.alpha(color),
                    (Color.red(color) + 50).coerceAtMost(255),
                    (Color.green(color) + 50).coerceAtMost(255),
                    (Color.blue(color) + 50).coerceAtMost(255)
                )
            }
        }
        existingGradientDrawable.colors = colors
        return existingGradientDrawable
    }

    fun getDominantColor(bitmap: Bitmap?): Int {
        val newBitmap = Bitmap.createScaledBitmap(bitmap!!, 1, 1, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()
        return lightenColor(color)
    }

    private fun darkenColor(color: Int): Int {
        val factor = 0.8f

        val alpha = Color.alpha(color)
        val red = (Color.red(color) * factor).toInt()
        val green = (Color.green(color) * factor).toInt()
        val blue = (Color.blue(color) * factor).toInt()

        return Color.argb(alpha, red, green, blue)
    }

    private fun lightenColor(color: Int): Int {
        val factor = 1.2f

        val alpha = Color.alpha(color)
        val red = (Color.red(color) * factor).toInt().coerceAtMost(255)
        val green = (Color.green(color) * factor).toInt().coerceAtMost(255)
        val blue = (Color.blue(color) * factor).toInt().coerceAtMost(255)

        return Color.argb(alpha, red, green, blue)
    }
}

fun convertDpToPixels(dp: Float): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun hexToRgb(hex: String): Triple<Int, Int, Int>? {
    if (hex.length != 7 || hex[0] != '#') {
        return null
    }

    return try {
        val r = hex.substring(1, 3).toInt(16)
        val g = hex.substring(3, 5).toInt(16)
        val b = hex.substring(5, 7).toInt(16)
        Triple(r, g, b)
    } catch (e: NumberFormatException) {
        null
    }
}
