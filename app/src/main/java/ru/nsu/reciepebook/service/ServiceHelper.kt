package ru.nsu.reciepebook.service


import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import ru.nsu.reciepebook.MainActivity
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.util.Constants
import ru.nsu.reciepebook.util.Constants.Companion.CANCEL_REQUEST_CODE
import ru.nsu.reciepebook.util.Constants.Companion.CLEAR_NOTIFICATION
import ru.nsu.reciepebook.util.Constants.Companion.CLICK_REQUEST_CODE
import ru.nsu.reciepebook.util.Constants.Companion.FROM_MAIN
import ru.nsu.reciepebook.util.Constants.Companion.NAV_DESTINATION
import ru.nsu.reciepebook.util.Constants.Companion.RESUME_REQUEST_CODE
import ru.nsu.reciepebook.util.Constants.Companion.STEP_NUMBER
import ru.nsu.reciepebook.util.Constants.Companion.STOPWATCH_STATE
import ru.nsu.reciepebook.util.Constants.Companion.STOP_REQUEST_CODE

@ExperimentalAnimationApi
object ServiceHelper {

    const val flag =
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

    fun clickPendingIntent(context: Context, stepNumber: Int, isRemove: Boolean = false): PendingIntent {
        val clickIntent = Intent(context, MainActivity::class.java).apply {
            putExtra(NAV_DESTINATION, Screen.CookingScreen.route)
            putExtra(STEP_NUMBER, stepNumber)
            if (isRemove){
                putExtra(CLEAR_NOTIFICATION, true)
                action = System.currentTimeMillis().toString()
            }
        }
        return PendingIntent.getActivity(
            context, CLICK_REQUEST_CODE, clickIntent, flag
        )
    }

    fun stopPendingIntent(context: Context): PendingIntent {
        val stopIntent = Intent(context, CountdownService::class.java).apply {
            putExtra(STOPWATCH_STATE, CountdownState.Stopped.name)
        }
        return PendingIntent.getService(
            context, STOP_REQUEST_CODE, stopIntent, flag
        )
    }

    fun resumePendingIntent(context: Context): PendingIntent {
        val resumeIntent = Intent(context, CountdownService::class.java).apply {
            putExtra(STOPWATCH_STATE, CountdownState.Started.name)
        }
        return PendingIntent.getService(
            context, RESUME_REQUEST_CODE, resumeIntent, flag
        )
    }

    fun cancelPendingIntent(context: Context): PendingIntent {
        val cancelIntent = Intent(context, CountdownService::class.java).apply {
            putExtra(STOPWATCH_STATE, CountdownState.Canceled.name)
        }
        return PendingIntent.getService(
            context, CANCEL_REQUEST_CODE, cancelIntent, flag
        )
    }

    fun triggerCountdownService(context: Context, action: String, stepNumber: Int) {
        Intent(context, CountdownService::class.java).apply {
            this.action = action
            putExtra(STEP_NUMBER, stepNumber)
            context.startService(this)
        }
    }
    fun cancelCountdownService(context: Context, fromMain: Boolean = false) {
        Intent(context, CountdownService::class.java).apply {
            this.action = Constants.ACTION_SERVICE_CANCEL
            if (fromMain)
                putExtra(FROM_MAIN, true)
            context.startService(this)
        }
    }
}