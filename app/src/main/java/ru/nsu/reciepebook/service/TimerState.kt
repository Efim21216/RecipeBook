package ru.nsu.reciepebook.service

data class TimerState(
    val hours: String = "00",
    val minutes: String = "00",
    val seconds: String = "00",
    val state: CountdownState = CountdownState.Idle
)