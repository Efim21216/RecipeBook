package ru.nsu.reciepebook.util

import android.app.Application
import java.io.File

class SampleProvider(
    private val application: Application,
) {
    fun provide(): File {
        val file = File(application.cacheDir, "newRonaldo.jpeg")
        file.createNewFile()
        file.outputStream().use { stream ->
            application.assets.open("image/ronaldo.jpeg").copyTo(stream) // assets/image/ronaldo.jpeg
        }
        return file
    }
}