package com.example.e_museum.utils

import android.graphics.Point
import android.util.Log
import java.io.Serializable
import kotlin.math.sqrt

fun getDistance(pointA: Point, pointB: Point): Float {
    val diffX = pointA.x - pointB.x
    val diffY = pointA.y - pointB.y
    return sqrt((diffX * diffX + diffY * diffY).toFloat())
}

fun <T : Serializable> printLogcat(string: T) {
    Log.d("app-debug", (string as Serializable).toString())
}

fun getReadableTime(time: Int): String {
    var s: Int = time / 1000
    val m = s / 60
    s %= 60
    return "$m:${s / 10}${s % 10}"
}

fun normalizeDate(dateGot: String): String {
    val dates = dateGot.split('-')
    return dates[2] + "/" + dates[1] + "/" + dates[0]
}