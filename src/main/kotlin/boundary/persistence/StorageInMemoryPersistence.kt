package boundary.persistence

import domain.*

class StorageInMemoryPersistence : StorageRepository {

    private val cabinets: MutableMap<StorageCabinetId, StorageCabinet> = mutableMapOf()


    override fun add(cabinet: StorageCabinet): Result<StorageCabinet> {
        if (cabinets.containsKey(cabinet.id)) {
            Result.failure<DomainErrors>(AlreadyExists(cabinet.id.toString()))
        }
        cabinets[cabinet.id] = cabinet
        return Result.success(cabinet)
    }

    override fun update(cabinet: StorageCabinet): Result<StorageCabinet> {
        if (!cabinets.containsKey(cabinet.id)) {
            Result.failure<DomainErrors>(NotFound(cabinet.id.toString()))
        }
        cabinets[cabinet.id] = cabinet
        return Result.success(cabinet)
    }

    override fun delete(cabinetId: StorageCabinetId): Result<Unit> {
        cabinets.remove(cabinetId)
            ?: return Result.failure(NotFound(cabinetId.toString()))
        return Result.success(Unit)
    }

    override fun findByName(name: StorageCabinetName): StorageCabinet? {
        return cabinets.filterValues { it.name == name }.entries.firstOrNull()?.value
    }

    override fun get(cabinetId: StorageCabinetId): Result<StorageCabinet> {
        return cabinets[cabinetId]?.let {
            Result.success(it)
        } ?: Result.failure(NotFound(cabinetId.toString()))
    }

    override fun getAll(): List<StorageCabinet> {
        return cabinets.values.toList()
    }
}