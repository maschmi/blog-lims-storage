package domain

class StorageValidator(private val repository: StorageRepository) {

    fun validateName(cabinetName: StorageCabinetName, existingId: StorageCabinetId? = null) : Result<StorageCabinetName> {
        val existingCabinet = repository.findByName(cabinetName)
        return if (existingCabinet != null && existingCabinet.id != existingId ) {
            Result.failure(ConstraintViolation("Name already used"))
        } else {
            Result.success(cabinetName)
        }
    }
}