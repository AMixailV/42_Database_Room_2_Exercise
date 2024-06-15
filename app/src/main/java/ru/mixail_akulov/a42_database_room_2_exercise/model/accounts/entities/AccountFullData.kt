package ru.mixail_akulov.a42_database_room_2_exercise.model.accounts.entities

import ru.mixail_akulov.a42_database_room_2_exercise.model.boxes.entities.BoxAndSettings

/**
 * Account info with all boxes and their settings
 */
data class AccountFullData(
    val account: Account,
    val boxesAndSettings: List<BoxAndSettings>
)