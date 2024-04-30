package application

import domain.StorageCabinet
import domain.StorageCabinetId
import domain.StorageCabinetName
import domain.StorageRepository

class DecomissionStorageCabinet(private val repository: StorageRepository) {

    fun execute(cabinetId: StorageCabinetId)  {
        repository.delete(cabinetId)
    }
}