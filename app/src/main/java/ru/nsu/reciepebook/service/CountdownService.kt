package ru.nsu.reciepebook.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Binder
import android.os.Build
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.util.Constants.Companion.ACTION_SERVICE_CANCEL
import ru.nsu.reciepebook.util.Constants.Companion.ACTION_SERVICE_START
import ru.nsu.reciepebook.util.Constants.Companion.ACTION_SERVICE_STOP
import ru.nsu.reciepebook.util.Constants.Companion.FROM_MAIN
import ru.nsu.reciepebook.util.Constants.Companion.NOTIFICATION_CHANNEL_ALARM_ID
import ru.nsu.reciepebook.util.Constants.Companion.NOTIFICATION_CHANNEL_ID
import ru.nsu.reciepebook.util.Constants.Companion.NOTIFICATION_ID
import ru.nsu.reciepebook.util.Constants.Companion.RECIPE_ID_ARG
import ru.nsu.reciepebook.util.Constants.Companion.STEP_NUMBER
import ru.nsu.reciepebook.util.Constants.Companion.STOPWATCH_STATE
import java.lang.IllegalArgumentException
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
    private var player: MediaPlayer? = null
    private var stepNumber: Int = 0
    private var recipeId: Int = 0

    override fun onBind(p0: Intent?) = binder

    @OptIn(ExperimentalAnimationApi::class)
    fun setNotificationClick(isRemove: Boolean = false) = notificationBuilder.setContentIntent(ServiceHelper
        .clickPendingIntent(this@CountdownService, stepNumber, recipeId, isRemove))

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //Обработка кликов по уведомлению
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
                stopService()
            }
        }
        //Обработка действий от приложения
        intent?.action.let {
            when (it) {
                ACTION_SERVICE_START -> {
                    setStopButton()
                    startForegroundService()
                    val step = intent?.getIntExtra(STEP_NUMBER, -1)
                    val recipe = intent?.getIntExtra(RECIPE_ID_ARG, -1)
                    if (step == -1 || recipe == -1)
                        throw IllegalArgumentException("Pass step number and recipe id")
                    stepNumber = step!!
                    recipeId = recipe!!
                    startCountdown { hours, minutes, seconds ->
                        updateNotification(hours = hours, minutes = minutes, seconds = seconds)
                    }
                }
                ACTION_SERVICE_STOP -> {
                    stopStopwatch()
                    setResumeButton()
                }
                ACTION_SERVICE_CANCEL -> {
                    val fromMain = intent?.extras?.getBoolean(FROM_MAIN, false)
                    if (fromMain != null && fromMain
                        && _timerState.value.state == CountdownState.Canceled) {
                        stopStopwatch()
                        stopService()
                    }
                    if ((fromMain != null && !fromMain)
                        || fromMain == null) {
                        stopStopwatch()
                        stopService()
                    }
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
    fun setTime(durationInSeconds: Int) {
        duration = durationInSeconds.seconds
        updateTimeUnitsToDuration()
    }
    private fun stopService() {
        _timerState.update { it.copy(state = CountdownState.Idle) }
        player?.stop()
        player?.release()
        player = null
        updateTimeUnitsToDuration()
        stopForegroundService()
    }
    private fun startCountdown(onTick: (h: String, m: String, s: String) -> Unit) {
        _timerState.update { it.copy(state = CountdownState.Started) }
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            duration = duration.minus(1.seconds)
            updateTimeUnitsToDuration()
            onTick(timerState.value.hours,
                timerState.value.minutes,
                timerState.value.seconds)
            if (duration == Duration.ZERO){
                if (player == null)
                    player = MediaPlayer.create(this@CountdownService,
                        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
                player?.start()
                doneNotification()
                stopStopwatch(CountdownState.Canceled)
            }
        }
    }

    private fun stopStopwatch(state: CountdownState = CountdownState.Stopped) {
        if (this::timer.isInitialized) {
            timer.cancel()
        }
        _timerState.update { it.copy(state = state) }
    }


    private fun updateTimeUnitsToDuration() {
        duration.toComponents { hours, minutes, seconds, _ ->
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
            startForeground(NOTIFICATION_ID,
                setNotificationClick().build(),
                FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
        } else {
            startForeground(NOTIFICATION_ID, setNotificationClick().build())
        }
    }

    private fun stopForegroundService() {
        notificationManager.cancel(NOTIFICATION_ID)
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelDefault = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_ID,
                NotificationManager.IMPORTANCE_LOW
            )
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ALARM_ID,
                NOTIFICATION_CHANNEL_ALARM_ID,
                IMPORTANCE_HIGH
            )
            channel.setSound(null, null)
            notificationManager.createNotificationChannel(channel)
            notificationManager.createNotificationChannel(channelDefault)
        }
    }

    private fun updateNotification(hours: String, minutes: String, seconds: String) {
        notificationManager.notify(
            NOTIFICATION_ID,
            setNotificationClick().setContentText(
                formatTime(
                    hours = hours,
                    minutes = minutes,
                    seconds = seconds,
                )
            ).build()
        )
    }
    @OptIn(ExperimentalAnimationApi::class)
    private fun doneNotification() {
        notificationManager.cancel(NOTIFICATION_ID)
        val notification = NotificationCompat
            .Builder(this@CountdownService,
                NOTIFICATION_CHANNEL_ALARM_ID)
            .setContentTitle("Stopwatch")
            .setContentText("00:00:00")
            .setSmallIcon(R.drawable.baseline_timer_24)
            .setSound(null)
            .setPriority(IMPORTANCE_HIGH)
            .setContentIntent(ServiceHelper
                .clickPendingIntent(this@CountdownService, stepNumber, recipeId, true))
            .setOngoing(true).build()
        notificationManager.notify(
            NOTIFICATION_ID,
            notification
        )
    }

    @SuppressLint("RestrictedApi")
    @OptIn(ExperimentalAnimationApi::class)
    private fun setStopButton() {
        if (notificationBuilder.mActions.size > 0)
            notificationBuilder.mActions.removeAt(0)
        notificationBuilder.mActions.add(
            0,
            NotificationCompat.Action(
                0,
                "Stop",
                ServiceHelper.stopPendingIntent(this)
            )
        )
        notificationManager.notify(NOTIFICATION_ID, setNotificationClick().build())
    }
    @SuppressLint("RestrictedApi")
    @OptIn(ExperimentalAnimationApi::class)
    private fun setResumeButton() {
        if (notificationBuilder.mActions.size > 0)
            notificationBuilder.mActions.removeAt(0)
        notificationBuilder.mActions.add(
            0,
            NotificationCompat.Action(
                0,
                "Resume",
                ServiceHelper.resumePendingIntent(this)
            )
        )
        notificationManager.notify(NOTIFICATION_ID, setNotificationClick().build())
    }

    inner class CountdownBinder : Binder() {
        fun getService(): CountdownService = this@CountdownService
    }


    override fun onDestroy() {
        Log.d("MyTag", "DESTROY SERVICE")
        player?.release()
        player = null
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
