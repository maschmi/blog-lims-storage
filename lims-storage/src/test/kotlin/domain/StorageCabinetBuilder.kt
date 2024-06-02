package domain

import generateRandomString

class StorageCabinetBuilder {

    private var name = StorageCabinetName("Storage-${generateRandomString(10)}")
    private var room = RoomBuilder().build()
    private var id = StorageCabinetId.new()
    private var boxes = emptyList<StorageBox>()

    fun withStorageBoxes(boxes: List<StorageBox>): StorageCabinetBuilder {
       this.boxes = boxes
       return this
    }

    fun withId(id: StorageCabinetId): StorageCabinetBuilder {
        this.id = id
        return this
    }

    fun withName(name: String): StorageCabinetBuilder {
        this.name = StorageCabinetName(name)
        return this
    }

    fun withName(name: StorageCabinetName): StorageCabinetBuilder {
        this.name = name
        return this
    }


    fun withRoom(room: Room): StorageCabinetBuilder {
        this.room = room
        return this
    }

    fun build(): StorageCabinet {
        return StorageCabinet(id, name, room, boxes)
    }

}
