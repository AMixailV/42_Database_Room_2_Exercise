package ru.mixail_akulov.a42_database_room_2_exercise.utils.resources

import android.content.Context

class ContextResources(
    private val context: Context
) : Resources {

    override fun getString(stringRes: Int): String {
        return context.getString(stringRes)
    }

}