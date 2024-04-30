package domain

import flatMap
import java.util.*


class StorageCabinet(val id: StorageCabinetId, name: StorageCabinetName, room: Room, storageBoxes: List<StorageBox>) {

    var name = name
        private set

    var room = room
        private set

    private val _storageBoxes = storageBoxes.associateBy { it.id }.toMutableMap()

    val storageBoxes: List<StorageBox>
        get() = _storageBoxes.values.toList()


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

    fun addStorageBox(storageBox: StorageBox) : Result<StorageCabinet> {
        return if (_storageBoxes.containsKey(storageBox.id)) {
            Result.failure(AlreadyExists(storageBox.id.toString()))
        } else {
            _storageBoxes.put(storageBox.id, storageBox)
            Result.success(this)
        }
    }

    fun removeStorageBox(storageBoxId: StorageBoxId) : Result<StorageCabinet> {
        return if (!_storageBoxes.containsKey(storageBoxId)) {
            Result.failure(NotFound(storageBoxId.toString()))
        } else {
            _storageBoxes.remove(storageBoxId)
            Result.success(this)
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
                    val cabinet = StorageCabinet(StorageCabinetId.new(), it, room, emptyList())
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