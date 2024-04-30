package application

import domain.*

class CommissionStorageCabinet(private val repository: StorageRepository, private val validator: StorageValidator) {

    fun execute(name: StorageCabinetName, room : Room) : Result<StorageCabinet> {
        return StorageCabinet.commission(name, room, repository, validator)
    }
}