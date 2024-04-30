package application

import domain.StorageCabinet
import domain.StorageCabinetName
import domain.StorageRepository

class CommissionStorageCabinet(private val repository: StorageRepository) {

    fun execute(name: StorageCabinetName) : Result<StorageCabinet> {
        val cabinet = StorageCabinet.create(name)
        return repository.add(cabinet)
    }
}