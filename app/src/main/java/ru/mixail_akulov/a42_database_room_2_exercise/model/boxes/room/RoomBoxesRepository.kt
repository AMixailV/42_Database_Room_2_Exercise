package ru.mixail_akulov.a42_database_room_2_exercise.model.boxes.room

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import ru.mixail_akulov.a42_database_room_2_exercise.model.AuthException
import ru.mixail_akulov.a42_database_room_2_exercise.model.accounts.AccountsRepository
import ru.mixail_akulov.a42_database_room_2_exercise.model.boxes.BoxesRepository
import ru.mixail_akulov.a42_database_room_2_exercise.model.boxes.entities.Box
import ru.mixail_akulov.a42_database_room_2_exercise.model.boxes.entities.BoxAndSettings
import ru.mixail_akulov.a42_database_room_2_exercise.model.boxes.room.entities.AccountBoxSettingDbEntity
import ru.mixail_akulov.a42_database_room_2_exercise.model.room.wrapSQLiteException

class RoomBoxesRepository(
    private val accountsRepository: AccountsRepository,
    private val boxesDao: BoxesDao,
    private val ioDispatcher: CoroutineDispatcher
) : BoxesRepository {

    override suspend fun getBoxesAndSettings(onlyActive: Boolean): Flow<List<BoxAndSettings>> {
        return accountsRepository.getAccount()
            .flatMapLatest { account ->
                if (account == null) return@flatMapLatest flowOf(emptyList())
                queryBoxesAndSettings(account.id)
            }
            .mapLatest { boxAndSettings ->
                if (onlyActive) {
                    boxAndSettings.filter { it.isActive }
                } else {
                    boxAndSettings
                }
            }
    }

    override suspend fun activateBox(box: Box) = wrapSQLiteException(ioDispatcher) {
        setActiveFlagForBox(box, true)
    }

    override suspend fun deactivateBox(box: Box) = wrapSQLiteException(ioDispatcher) {
        setActiveFlagForBox(box, false)
    }

    private fun queryBoxesAndSettings(accountId: Long): Flow<List<BoxAndSettings>> {
        return boxesDao.getBoxesAndSettings(accountId)
            .map { entities ->
                entities.map {
                    // todo #7: использовать встроенные сущности вместо ключей и значений;
                    //  Теперь запустите проект и проверьте, как он работает.
                    val boxEntity = it.key
                    val settingsEntity = it.value
                    // todo #3: использовать встроенный объект вместо свойства isActive
                    BoxAndSettings(boxEntity.toBox(), settingsEntity == null || settingsEntity.isActive)
                                    }
            }
    }

    // todo #12: Rewrite queryBoxesAndSettings() method above ^ for usage with database view.
    //           Uninstall the app from the device, install it again, launch and check all things work correctly.

    // todo #16: Rewrite queryBoxesAndSettings() method above ^ again for usage with SettingWithEntitiesTuple.
    //           Uninstall the app from the device, install it again, launch and check all things work correctly.

    private suspend fun setActiveFlagForBox(box: Box, isActive: Boolean) {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        boxesDao.setActiveFlagForBox(
            AccountBoxSettingDbEntity(
                accountId = account.id,
                boxId = box.id,
                // todo #4: use embedded entity instead of isActive property
                isActive = isActive
            )
        )
    }
}