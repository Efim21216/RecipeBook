package ru.nsu.reciepebook.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
import android.os.Binder
import android.os.Build
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.nsu.reciepebook.util.Constants.Companion.ACTION_SERVICE_CANCEL
import ru.nsu.reciepebook.util.Constants.Companion.ACTION_SERVICE_START
import ru.nsu.reciepebook.util.Constants.Companion.ACTION_SERVICE_STOP
import ru.nsu.reciepebook.util.Constants.Companion.NOTIFICATION_CHANNEL_ID
import ru.nsu.reciepebook.util.Constants.Companion.NOTIFICATION_CHANNEL_NAME
import ru.nsu.reciepebook.util.Constants.Companion.NOTIFICATION_ID
import ru.nsu.reciepebook.util.Constants.Companion.STOPWATCH_STATE
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
@AndroidEntryPoint
class CountdownService: Service() {
    @Inject
    lateinit var notificationManager: NotificationManager
    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder
    private val binder = CountdownBinder()
    private var duration: Duration = Duration.ZERO
    private lateinit var timer: Timer
    private val _timerState = MutableStateFlow(TimerState())
    val timerState: StateFlow<TimerState> = _timerState.asStateFlow()

    override fun onBind(p0: Intent?) = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.getStringExtra(STOPWATCH_STATE)) {
            CountdownState.Started.name -> {
                setStopButton()
                startForegroundService()
                startCountdown { hours, minutes, seconds ->
                    updateNotification(hours = hours, minutes = minutes, seconds = seconds)
                }
            }
            CountdownState.Stopped.name -> {
                stopStopwatch()
                setResumeButton()
            }
            CountdownState.Canceled.name -> {
                stopStopwatch()
                cancelStopwatch()
                stopForegroundService()
            }
        }
        intent?.action.let {
            when (it) {
                ACTION_SERVICE_START -> {
                    setStopButton()
                    startForegroundService()
                    startCountdown { hours, minutes, seconds ->
                        updateNotification(hours = hours, minutes = minutes, seconds = seconds)
                    }
                }
                ACTION_SERVICE_STOP -> {
                    stopStopwatch()
                    setResumeButton()
                }
                ACTION_SERVICE_CANCEL -> {
                    stopStopwatch()
                    cancelStopwatch()
                    stopForegroundService()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
    fun setTime(durationInSeconds: Int) {
        duration = durationInSeconds.seconds
        updateTimeUnits()
    }
    private fun startCountdown(onTick: (h: String, m: String, s: String) -> Unit) {
        //currentState.value = CountdownState.Started
        _timerState.update { it.copy(state = CountdownState.Started) }
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            duration = duration.minus(1.seconds)
            updateTimeUnits()
            //onTick(hours.value, minutes.value, seconds.value)
            onTick(timerState.value.hours,
                timerState.value.minutes,
                timerState.value.seconds)
            if (duration == Duration.ZERO)
                stopStopwatch()
        }
    }

    private fun stopStopwatch() {
        if (this::timer.isInitialized) {
            timer.cancel()
        }
        //currentState.value = CountdownState.Stopped
        _timerState.update { it.copy(state = CountdownState.Started) }
    }

    private fun cancelStopwatch() {
        duration = Duration.ZERO
        //currentState.value = CountdownState.Idle
        _timerState.update { it.copy(state = CountdownState.Idle) }
        updateTimeUnits()
    }

    private fun updateTimeUnits() {
        duration.toComponents { hours, minutes, seconds, _ ->
            //this@CountdownService.hours.value = hours.toInt().pad()
            //this@CountdownService.minutes.value = minutes.pad()
            //this@CountdownService.seconds.value = seconds.pad()
            _timerState.update { it.copy(
                hours = hours.toInt().pad(),
                minutes = minutes.pad(),
                seconds = seconds.pad()
            ) }
        }
    }

    private fun startForegroundService() {
        createNotificationChannel()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(NOTIFICATION_ID, notificationBuilder.build(), FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
        } else {
            startForeground(NOTIFICATION_ID, notificationBuilder.build())
        }
    }

    private fun stopForegroundService() {
        notificationManager.cancel(NOTIFICATION_ID)
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun updateNotification(hours: String, minutes: String, seconds: String) {
        notificationManager.notify(
            NOTIFICATION_ID,
            notificationBuilder.setContentText(
                formatTime(
                    hours = hours,
                    minutes = minutes,
                    seconds = seconds,
                )
            ).build()
        )
    }

    @SuppressLint("RestrictedApi")
    @OptIn(ExperimentalAnimationApi::class)
    private fun setStopButton() {
        notificationBuilder.mActions.removeAt(0)
        notificationBuilder.mActions.add(
            0,
            NotificationCompat.Action(
                0,
                "Stop",
                ServiceHelper.stopPendingIntent(this)
            )
        )
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
    @SuppressLint("RestrictedApi")
    @OptIn(ExperimentalAnimationApi::class)
    private fun setResumeButton() {
        notificationBuilder.mActions.removeAt(0)
        notificationBuilder.mActions.add(
            0,
            NotificationCompat.Action(
                0,
                "Resume",
                ServiceHelper.resumePendingIntent(this)
            )
        )
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    inner class CountdownBinder : Binder() {
        fun getService(): CountdownService = this@CountdownService
    }


    override fun onDestroy() {
        Log.d("MyTag", "DESTROY SERVICE")
        super.onDestroy()
    }

    override fun onCreate() {
        Log.d("MyTag", "ON CREATE SERVICE")
        super.onCreate()
    }
}
enum class CountdownState {
    Idle,
    Started,
    Stopped,
    Canceled
}
fun formatTime(seconds: String, minutes: String, hours: String): String {
    return "$hours:$minutes:$seconds"
}

fun Int.pad(): String {
    return this.toString().padStart(2, '0')
}
fun Long.pad(): String {
    return this.toString().padStart(2, '0')
}
