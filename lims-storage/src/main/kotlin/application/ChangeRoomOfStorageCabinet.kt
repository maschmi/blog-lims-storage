package application

import domain.*
import flatMap

class ChangeRoomOfStorageCabinet(private val repository: StorageRepository, private val validator: StorageValidator) {

    fun execute(id: StorageCabinetId, newRoom: Room): Result<StorageCabinet> {
        return repository.get(id)
            .flatMap { it.updateRoom(newRoom) }
            .flatMap { repository.update(it) }
    }
}