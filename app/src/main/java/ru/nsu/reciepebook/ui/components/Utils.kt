package ru.nsu.reciepebook.ui.components

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun DrawScope.rightBorder(width: Float, color: Color) {
    val canvasWidth = size.width - width / 2
    val canvasHeight = size.height
    drawLine(
        start = Offset(x = canvasWidth, y = 0f),
        end = Offset(x = canvasWidth, y = canvasHeight),
        color = color,
        strokeWidth = width
    )
}
fun DrawScope.leftBorder(width: Float, color: Color) {
    val canvasHeight = size.height
    drawLine(
        start = Offset(x = 0f + width / 2, y = 0f),
        end = Offset(x = 0f + width / 2, y = canvasHeight),
        color = color,
        strokeWidth = width
    )
}
fun DrawScope.topBorder(width: Float, color: Color) {
    val canvasWidth = size.width
    drawLine(
        start = Offset(x = 0f, y = 0f + width / 2),
        end = Offset(x = canvasWidth, y = 0f + width / 2),
        color = color,
        strokeWidth = width
    )
}
fun DrawScope.bottomBorder(width: Float, color: Color) {
    val canvasWidth = size.width
    val canvasHeight = size.height - width / 2
    drawLine(
        start = Offset(x = 0f, y = canvasHeight),
        end = Offset(x = canvasWidth, y = canvasHeight),
        color = color,
        strokeWidth = width
    )
}
fun convertLongToTime(time: Long): String {
    val date = Date(time * 1000)
    val format = SimpleDateFormat("HH:mm:ss", Locale.US)
    return format.format(date)
}