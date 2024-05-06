package domain

import TestDataFactory
import boundary.persistence.StorageInMemoryPersistence
import java.util.*

object TestFixtureFactory {
    /**
     * Creates a default Storage Cabinet with fixed names and ids als adds it to a repository
     */
    fun createStorageCabinetFixture() : StorageCabinetTestFixture {
        val repository = StorageInMemoryPersistence()
        val boxId = StorageBoxId(UUID.fromString("95a82777-8ae7-4fad-b8e6-4e2585dcdc4e"))
        val box = TestDataFactory.storageBoxBuilder().withId(boxId).withName("Default Box 0").build()

        val room = TestDataFactory.roomBuilder().withName(RoomName("Room 0")).build()

        val cabinetId = StorageCabinetId(UUID.fromString("a328d1b0-9b34-482f-8e17-848caed1ae1f"))
        val cabinet = TestDataFactory.storageCabinetBuilder()
            .withId(cabinetId)
            .withRoom(room)
            .withName("Cabinet 0")
            .withStorageBoxes(listOf(box))
            .build()

        repository.add(cabinet)
        return StorageCabinetTestFixture(cabinet, repository)
    }

}

class StorageCabinetTestFixture(val cabinet: StorageCabinet, val repository: StorageRepository) {
    val boxInCabinet = cabinet.storageBoxes.first()
    val room = cabinet.room
    val validator = StorageValidator(repository)

    fun createRandomNewCabinet() = TestDataFactory.createEmptyDefaultStorageCabinet()
    fun createRandomCabinet() = TestDataFactory.createRandomDefaultStorageCabinet()

    fun addEmptyCabinet() = repository.add(TestDataFactory.createEmptyDefaultStorageCabinet()).getOrThrow()
    fun addRandomCabinet() = repository.add(TestDataFactory.createRandomDefaultStorageCabinet()).getOrThrow()

}