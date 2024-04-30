package domain

import TestDataFactory
import boundary.persistence.StorageInMemoryPersistence
import copy
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
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
            val expectedCabinet = TestDataFactory.createRandomDefaultStorageCabinet()

            val newCabinet = StorageCabinet.commission(expectedCabinet.name, expectedCabinet.room, repository, validator)

            newCabinet shouldBeSuccess {
                it.shouldBeEqualToIgnoringFields(expectedCabinet, StorageCabinet::id)
            }
        }

        it("returns already existing when cabinet with same name is already present") {
            val repository = StorageInMemoryPersistence()
            val validator = StorageValidator(repository)
            val duplicatedName = StorageCabinetName("Cabinet 000")
            val room = TestDataFactory.createRandomDefaultRoom()
            StorageCabinet.commission(duplicatedName, room, repository, validator)

            val newCabinet = StorageCabinet.commission(duplicatedName, room, repository, validator)

            newCabinet shouldBeFailure {
                it shouldBeEqualUsingFields ConstraintViolation("Name already used")
            }
        }
    }

    describe("Decommission a storage cabinet") {
        it("does nothing if it was already decommissioned or never there") {
            val repository = StorageInMemoryPersistence()
            val existingCabinet = TestDataFactory.createRandomDefaultStorageCabinet()
            repository.add(existingCabinet)
            val idOfNotExistingCabinet = StorageCabinetId.new()
            require(repository.getAll().size == 1)

            StorageCabinet.decommission(idOfNotExistingCabinet, repository)

            repository.getAll() shouldContainExactly listOf(existingCabinet)
        }

        it("removes the storage cabinet from the repository") {
            val repository = StorageInMemoryPersistence()
            val existingCabinet = TestDataFactory.createRandomDefaultStorageCabinet()
            repository.add(existingCabinet)
            require(repository.getAll().size == 1)

            StorageCabinet.decommission(existingCabinet.id, repository)

            repository.getAll() shouldHaveSize 0
        }
    }

    describe("Update name of storage cabinet") {
        it("updates cabinet name if the name is not yet existing in the repository") {
            val repository = StorageInMemoryPersistence()
            val validator = StorageValidator(repository)
            repository.add(TestDataFactory.createRandomDefaultStorageCabinet())
            val existingCabinet = TestDataFactory.createRandomDefaultStorageCabinet()
            repository.add(existingCabinet)

            val expectedCabinet = existingCabinet.copy(name = StorageCabinetName("newName"))
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
            val otherCabinet = TestDataFactory.createRandomDefaultStorageCabinet()
            repository.add(otherCabinet)
            val existingCabinet = TestDataFactory.createRandomDefaultStorageCabinet()
            repository.add(existingCabinet)

            require(repository.getAll().size == 2)

            val result = existingCabinet.updateName(otherCabinet.name, validator)

            result shouldBeFailure {
                it shouldBeEqualUsingFields ConstraintViolation("Name already used")
            }
        }
    }

    describe("Update room of storage cabinet") {
        it("updates room") {
            val cabinet = TestDataFactory.createRandomDefaultStorageCabinet()
            val newRoom = TestDataFactory.createRandomDefaultRoom()
            require(cabinet.room != newRoom)
            val expectedCabinet = cabinet.copy(room = newRoom)

            cabinet.updateRoom(newRoom)

            cabinet shouldBeEqualToComparingFields expectedCabinet
        }
    }
})
