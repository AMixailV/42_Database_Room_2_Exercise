package ru.mixail_akulov.a42_database_room_2_exercise.model.accounts.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.mixail_akulov.a42_database_room_2_exercise.model.accounts.entities.Account
import ru.mixail_akulov.a42_database_room_2_exercise.model.accounts.entities.SignUpData

@Entity(
    tableName = "accounts",
    indices = [
        Index("email", unique = true)
    ]
)
data class AccountDbEntity(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "email", collate = ColumnInfo.NOCASE) val email: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "created_at") val createdAt: Long
) {

    fun toAccount(): Account = Account(
        id = id,
        email = email,
        username = username,
        createdAt = createdAt
    )

    companion object {
        fun fromSignUpData(signUpData: SignUpData): AccountDbEntity {
            return AccountDbEntity(
                id = 0, // SQLite generates identifier automatically if ID = 0
                email = signUpData.email,
                username = signUpData.username,
                password = String(signUpData.password),
                createdAt = System.currentTimeMillis()
            )
        }
    }
}
