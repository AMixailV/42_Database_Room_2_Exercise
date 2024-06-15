package ru.mixail_akulov.a42_database_room_2_exercise.utils.resources

import androidx.annotation.StringRes

interface Resources {
    fun getString(@StringRes stringRes: Int): String
}