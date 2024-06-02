import domain.*


fun generateRandomString(length: Int): String {
    val allowedChars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}


fun StorageCabinet.copy(
    id: StorageCabinetId = this.id,
    name: StorageCabinetName = this.name,
    room: Room = this.room,
    storageBoxes: List<StorageBox> = this.storageBoxes
) = StorageCabinet(id, name, room, storageBoxes)

fun StorageBox.copy(
    id: StorageBoxId = this.id,
    name: StorageBoxName,
    description: StorageBoxDescription
) = StorageBox(id, name, description)
