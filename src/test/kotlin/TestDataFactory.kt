import domain.RoomBuilder
import domain.StorageCabinetBuilder

object TestDataFactory {

    fun storageCabinetBuilder() = StorageCabinetBuilder()
    fun createRandomDefaultStorageCabinet() = StorageCabinetBuilder().build()

    fun roomBuilder() = RoomBuilder()
    fun createRandomDefaultRoom() = RoomBuilder().build()
}