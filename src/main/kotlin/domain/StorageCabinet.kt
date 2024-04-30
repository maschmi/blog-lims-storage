package domain

import java.util.*


class StorageCabinet(val id: StorageCabinetId, name: StorageCabinetName) {

    var name = name
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

    fun updateName(newName: StorageCabinetName) {
        this.name = newName
    }

    companion object {
        fun create(name: StorageCabinetName): StorageCabinet {
            return StorageCabinet(
                StorageCabinetId(UUID.randomUUID()),
                name
            )
        }
    }
}

@JvmInline
value class StorageCabinetName(val name: String)

@JvmInline
value class StorageCabinetId(val id: UUID)
