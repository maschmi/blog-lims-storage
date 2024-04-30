package domain

interface StorageRepository {

    fun add(cabinet: StorageCabinet): Result<StorageCabinet>
    fun update(cabinet: StorageCabinet): Result<StorageCabinet>
    fun delete(cabinet: StorageCabinetId): Result<Unit>

    fun get(cabinetId: StorageCabinetId) : Result<StorageCabinet>
    fun getAll() : List<StorageCabinet>
}