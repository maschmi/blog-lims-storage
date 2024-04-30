package domain

import boundary.persistence.StorageInMemoryPersistence
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equality.shouldBeEqualToIgnoringFields
import io.kotest.matchers.equality.shouldBeEqualUsingFields
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe

class StorageCabinetTest : DescribeSpec({

    describe("Commission a new StorageCabinet") {
        it("adds new cabinet to repository and returns it") {
            val repository = StorageInMemoryPersistence()
            val validator = StorageValidator(repository)
            val newName = StorageCabinetName("Cabinet 000")
            val expectedCabinet = StorageCabinet(StorageCabinetId.new(), newName)

            val newCabinet = StorageCabinet.commission(newName, repository, validator)

            newCabinet shouldBeSuccess {
                it.shouldBeEqualToIgnoringFields(expectedCabinet, StorageCabinet::id)
            }
        }

        it("returns already existing when cabinet with same name is already present") {
            val repository = StorageInMemoryPersistence()
            val validator = StorageValidator(repository)
            val duplicatedName = StorageCabinetName("Cabinet 000")
            StorageCabinet.commission(duplicatedName, repository, validator)

            val newCabinet = StorageCabinet.commission(duplicatedName, repository, validator)

            newCabinet shouldBeFailure {
                it shouldBeEqualUsingFields ConstraintViolation("Name already used")
            }
        }
    }

    describe("Decommission a storage cabinet") {
        it("does nothing if it was already decommissioned or never there") {
            val repository = StorageInMemoryPersistence()
            val validator = StorageValidator(repository)
            val existingCabinet = StorageCabinet.commission(StorageCabinetName("existing"), repository, validator).getOrThrow()
            val idOfNotExistingCabinet = StorageCabinetId.new()
            require(repository.getAll().size == 1)

            StorageCabinet.decommission(idOfNotExistingCabinet, repository)

            repository.getAll() shouldContainExactly listOf(existingCabinet)
        }

        it("removes the storage cabinet from the repository") {
            val repository = StorageInMemoryPersistence()
            val validator = StorageValidator(repository)
            val existingCabinet = StorageCabinet.commission(StorageCabinetName("existing"), repository, validator).getOrThrow()
            require(repository.getAll().size == 1)

            StorageCabinet.decommission(existingCabinet.id, repository)

            repository.getAll() shouldHaveSize 0
        }
    }

    describe("Update name of storage cabinet") {
        it("updates cabinet name if the name is not yet existing in the repository") {
            val repository = StorageInMemoryPersistence()
            val validator = StorageValidator(repository)
            StorageCabinet.commission(StorageCabinetName("other"), repository, validator).getOrThrow()
            val existingCabinet = StorageCabinet.commission(StorageCabinetName("existing"), repository, validator).getOrThrow()
            val expectedCabinet = StorageCabinet(existingCabinet.id, StorageCabinetName("newName"))
            require(repository.getAll().size == 2)

            val result = existingCabinet.updateName(expectedCabinet.name, validator)

            // returns the updated cabinet
            result shouldBeSuccess {
                it shouldBeEqualUsingFields expectedCabinet
            }

            // makes sure the instance is updated
            existingCabinet shouldBeEqualUsingFields expectedCabinet
        }

        it("returns failure when name is already existing in repository") {
            val repository = StorageInMemoryPersistence()
            val validator = StorageValidator(repository)
            val otherCabinet =  StorageCabinet.commission(StorageCabinetName("other"), repository, validator).getOrThrow()
            val existingCabinet = StorageCabinet.commission(StorageCabinetName("existing"), repository, validator).getOrThrow()
            require(repository.getAll().size == 2)

            val result = existingCabinet.updateName(otherCabinet.name, validator)

            result shouldBeFailure {
                it shouldBeEqualUsingFields ConstraintViolation("Name already used")
            }
        }
    }


})
