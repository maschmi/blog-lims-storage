package domain

import flatMap
import java.util.*


class StorageCabinet(val id: StorageCabinetId, name: StorageCabinetName, room: Room) {

    var name = name
        private set

    var room = room
        private set


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StorageCabinet

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun updateName(newName: StorageCabinetName, validator: StorageValidator) : Result<StorageCabinet> {
        return validator.validateName(newName, this.id)
            .map {
                this.name = it
                this
            }
    }

    // we could opt to return nothing or only StorageCabinet
    // However, returning a Result makes the API of the StorageCabinet consistent
    fun updateRoom(newRoom: Room) : Result<StorageCabinet> {
        this.room = newRoom
        return Result.success(this)
    }


    companion object {
        fun commission(name: StorageCabinetName, room: Room, repository: StorageRepository, validator: StorageValidator): Result<StorageCabinet> {
            return validator.validateName(name)
                .flatMap {
                    val cabinet = StorageCabinet(StorageCabinetId.new(), it, room)
                    repository.add(cabinet)
                }
        }

        fun decommission(id: StorageCabinetId, repository: StorageRepository) {
            repository.delete(id)
        }
    }
}

@JvmInline
value class StorageCabinetName(val name: String)

@JvmInline
value class StorageCabinetId(val id: UUID) {
    companion object {
        fun new(): StorageCabinetId {
            return StorageCabinetId(UUID.randomUUID())
        }
    }
}