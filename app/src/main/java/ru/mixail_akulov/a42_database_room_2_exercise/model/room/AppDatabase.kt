package ru.mixail_akulov.a42_database_room_2_exercise.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.mixail_akulov.a42_database_room_2_exercise.model.accounts.room.AccountsDao
import ru.mixail_akulov.a42_database_room_2_exercise.model.accounts.room.entities.AccountDbEntity
import ru.mixail_akulov.a42_database_room_2_exercise.model.boxes.room.BoxesDao
import ru.mixail_akulov.a42_database_room_2_exercise.model.boxes.room.entities.AccountBoxSettingDbEntity
import ru.mixail_akulov.a42_database_room_2_exercise.model.boxes.room.entities.BoxDbEntity

@Database(
    // todo #10: increment DB version to 2 because we've added a new database view
    version = 1,
    entities = [
        AccountDbEntity::class,
        BoxDbEntity::class,
        AccountBoxSettingDbEntity::class
    ],
    // todo #9: add a reference to the created database view here (use 'views' argument)
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAccountsDao(): AccountsDao

    abstract fun getBoxesDao(): BoxesDao

}