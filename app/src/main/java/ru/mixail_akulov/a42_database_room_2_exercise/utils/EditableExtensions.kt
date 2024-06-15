package ru.mixail_akulov.a42_database_room_2_exercise.utils

import android.text.Editable

fun Editable?.toCharArray(): CharArray {
    if (this == null) return CharArray(0)
    return CharArray(length).also {
        getChars(0, length, it, 0)
    }
}