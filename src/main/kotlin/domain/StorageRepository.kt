package domain

interface StorageRepository {

    fun add(cabinet: StorageCabinet): Result<StorageCabinet>
    fun update(cabinet: StorageCabinet): Result<StorageCabinet>
    fun delete(cabinetId: StorageCabinetId): Result<Unit>

    fun findByName(name: StorageCabinetName) : StorageCabinet?
    fun get(cabinetId: StorageCabinetId) : Result<StorageCabinet>
    fun getAll() : List<StorageCabinet>
}