package application

import domain.*
import flatMap

class ChangeNameOfStorageCabinet(private val repository: StorageRepository, private val validator: StorageValidator) {

    fun execute(id: StorageCabinetId, newName: StorageCabinetName): Result<StorageCabinet> {
        return repository.get(id)
            .flatMap { it.updateName(newName, validator) }
            .flatMap { repository.update(it) }
    }
}