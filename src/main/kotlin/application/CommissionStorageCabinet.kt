package application

import domain.StorageCabinet
import domain.StorageCabinetName
import domain.StorageRepository
import domain.StorageValidator

class CommissionStorageCabinet(private val repository: StorageRepository, private val validator: StorageValidator) {

    fun execute(name: StorageCabinetName) : Result<StorageCabinet> {
        return StorageCabinet.commission(name, repository, validator)
    }
}