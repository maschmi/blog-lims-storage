package application

import domain.StorageCabinet
import domain.StorageCabinetId
import domain.StorageCabinetName
import domain.StorageRepository

class ChangeNameOfStorageCabinet(private val repository: StorageRepository) {

    fun execute(id: StorageCabinetId, newName: StorageCabinetName) : Result<Result<StorageCabinet>> {
        return repository.get(id).map { cabinet ->
            cabinet.updateName(newName)
            repository.update(cabinet)

        }
    }
}