import domain.Room
import domain.StorageCabinet
import domain.StorageCabinetId
import domain.StorageCabinetName


fun generateRandomString(length: Int): String {
    val allowedChars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}


fun StorageCabinet.copy(id: StorageCabinetId = this.id, name: StorageCabinetName  = this.name, room: Room = this.room) =
    StorageCabinet(id, name, room)