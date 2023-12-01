package com.example.e_museum.utils

import android.graphics.Point
import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.util.Log
import java.io.Serializable
import kotlin.math.sqrt


fun getDistance(pointA: Point, pointB: Point): Float {
    val diffX = pointA.x - pointB.x
    val diffY = pointA.y - pointB.y
    return sqrt((diffX * diffX + diffY * diffY).toFloat())
}

fun <T: Serializable> printLogcat(string: T) {
    Log.d("app-debug", (string as Serializable).toString())
}