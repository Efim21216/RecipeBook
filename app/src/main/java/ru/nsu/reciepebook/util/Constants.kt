package ru.nsu.reciepebook.util

class Constants {
    companion object {
        const val BASE_URL = "http://10.0.2.2:8080" //for android
        const val AUTH = "Bearer "
        const val ACCESS_TOKEN = "Access token"
        const val REFRESH_TOKEN = "Refresh token"
        const val TOKEN_DATA_STORE = "Token data store"
        const val RECIPE_ID_ARG = "recipeId"
        const val TAGS_ARG = "tags"


        const val ACTION_SERVICE_START = "ACTION_SERVICE_START"
        const val ACTION_SERVICE_STOP = "ACTION_SERVICE_STOP"
        const val ACTION_SERVICE_CANCEL = "ACTION_SERVICE_CANCEL"

        const val STOPWATCH_STATE = "STOPWATCH_STATE"
        const val NAV_DESTINATION = "NAV_DESTINATION"
        const val STEP_NUMBER = "STEP_NUMBER"
        const val CLEAR_NOTIFICATION = "CLEAR"
        const val FROM_MAIN = "FROM_MAIN"

        const val NOTIFICATION_CHANNEL_ID = "Timer notification"
        const val NOTIFICATION_CHANNEL_ALARM_ID = "Alarm notification"
        const val NOTIFICATION_ID = 10

        const val CLICK_REQUEST_CODE = 100
        const val CANCEL_REQUEST_CODE = 101
        const val STOP_REQUEST_CODE = 102
        const val RESUME_REQUEST_CODE = 103
        val ALL_TAGS = listOf("#быстро","#великолепный","#вкусно","#домашняя","#диета","#здоровая")
    }
    enum class Measures(val value: String) {
        KILOGRAM("kilogram"),
        GRAM("gram"),
        MILLIGRAM("milligram"),
        LITER("liter"),
        MILLILITER("milliliter"),
        TEE_SPOON("tee_spoon"),
        TABLE_SPOON("table_spoon"),
        PIECE("piece")
    }
}