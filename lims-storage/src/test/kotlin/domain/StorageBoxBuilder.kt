package domain

import generateRandomString

class StorageBoxBuilder {

    private var id = StorageBoxId.new()
    private var name = StorageBoxName("Box-${generateRandomString(10)}")
    private var description = StorageBoxDescription(generateRandomString(400))

    fun withId(id: StorageBoxId) : StorageBoxBuilder {
        this.id = id
        return this
    }

    fun withName(name: String) : StorageBoxBuilder {
        this.name = StorageBoxName(name)
        return this
    }

    fun withDescription(description: String) : StorageBoxBuilder {
        this.description = StorageBoxDescription(description)
        return this
    }

    fun build(): StorageBox {
        return StorageBox(id, name, description)
    }
}
