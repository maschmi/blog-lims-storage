package application

import domain.StorageCabinet
import domain.StorageRepository

class GetListOfStorageCabinets(private val repository: StorageRepository) {
    fun execute() : List<StorageCabinet> {
        return repository.getAll()
    }
}