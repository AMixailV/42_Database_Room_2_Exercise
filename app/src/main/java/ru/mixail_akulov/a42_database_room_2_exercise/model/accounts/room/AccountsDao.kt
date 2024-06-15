package ru.mixail_akulov.a42_database_room_2_exercise.model.accounts.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.mixail_akulov.a42_database_room_2_exercise.model.accounts.room.entities.AccountDbEntity
import ru.mixail_akulov.a42_database_room_2_exercise.model.accounts.room.entities.AccountSignInTuple
import ru.mixail_akulov.a42_database_room_2_exercise.model.accounts.room.entities.AccountUpdateUsernameTuple

@Dao
interface AccountsDao {
    @Query("SELECT id, password FROM accounts WHERE email = :email")
    suspend fun findByEmail(email: String): AccountSignInTuple?

    @Update(entity = AccountDbEntity::class)
    suspend fun updateUsername(account: AccountUpdateUsernameTuple)

    @Insert(entity = AccountDbEntity::class)
    suspend fun createAccount(accountDbEntity: AccountDbEntity)

    @Query("SELECT * FROM accounts WHERE id = :accountId")
    fun getById(accountId: Long): Flow<AccountDbEntity?>

    // todo #18: Add a method for fetching boxes with edited settings (AccountAndEditedBoxesTuple)
    //           by account id

    // todo #20: Add a method for fetching all data from the database: all accounts, their settings
    //           and all related boxes.
}
