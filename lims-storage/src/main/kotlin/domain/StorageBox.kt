package domain

import java.util.*

class StorageBox internal constructor(val id: StorageBoxId, name: StorageBoxName, description: StorageBoxDescription) {

    var name: StorageBoxName = name
        private set

    var description: StorageBoxDescription = description
        private set


    companion object {
        internal fun new(name: StorageBoxName, description: StorageBoxDescription): StorageBox {
            return StorageBox(StorageBoxId(UUID.randomUUID()),name, description)
        }
    }

    override fun toString(): String {
        return "StorageBox(id=$id, name=$name, description=$description)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StorageBox

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

@JvmInline
value class StorageBoxId(val value: UUID) {
    override fun toString(): String {
        return "StorageBoxId(value=$value)"
    }
    companion object {
        fun new(): StorageBoxId {
            return StorageBoxId(UUID.randomUUID())
        }
    }
}

@JvmInline
value class StorageBoxName(val value: String) {
    override fun toString(): String {
        return "StorageBoxName(value='$value')"
    }
}

@JvmInline
value class StorageBoxDescription(val value: String) {
    override fun toString(): String {
        return "ContentDescription(value='$value')"
    }
}