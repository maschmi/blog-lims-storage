import domain.RoomBuilder
import domain.StorageBoxBuilder
import domain.StorageCabinetBuilder

object TestDataFactory {

    fun storageCabinetBuilder() = StorageCabinetBuilder()
    fun createRandomDefaultStorageCabinet() = StorageCabinetBuilder().withStorageBoxes(listOf(StorageBoxBuilder().build())).build()

    fun roomBuilder() = RoomBuilder()
    fun createRandomDefaultRoom() = RoomBuilder().build()

    fun storageBoxBuilder() = StorageBoxBuilder()
    fun createRandomDefaultStorageBox() = StorageBoxBuilder().build()
}