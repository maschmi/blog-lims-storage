package domain

import TestDataFactory
import boundary.persistence.StorageInMemoryPersistence
import copy
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.equality.shouldBeEqualToIgnoringFields
import io.kotest.matchers.equality.shouldBeEqualUsingFields
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe

class StorageCabinetTest : DescribeSpec({

    describe("Commission a new StorageCabinet") {
        it("adds new empty cabinet to repository and returns it") {
            val repository = StorageInMemoryPersistence()
            val validator = StorageValidator(repository)
            val expectedCabinet = TestDataFactory.createEmptyDefaultStorageCabinet()

            val newCabinet =
                StorageCabinet.commission(expectedCabinet.name, expectedCabinet.room, repository, validator)

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

            val result = StorageCabinet.decommission(idOfNotExistingCabinet, repository)
            result.shouldBeFailure(NotFound(idOfNotExistingCabinet.toString()))

            repository.getAll() shouldContainExactly listOf(existingCabinet)
        }

        it("returns InUseError when Storage Cabinet is not empty") {
            val repository = StorageInMemoryPersistence()
            val existingCabinet = TestDataFactory.createRandomDefaultStorageCabinet()
            repository.add(existingCabinet)
            require(repository.getAll().size == 1)

            val result = StorageCabinet.decommission(existingCabinet.id, repository)

            result.shouldBeFailure(InUseException(existingCabinet.id.toString()))
            repository.getAll() shouldContainExactly listOf(existingCabinet)
        }

        it("removes empty StorageCabinet from repository") {
            val repository = StorageInMemoryPersistence()
            val existingCabinet = TestDataFactory.createEmptyDefaultStorageCabinet()
            repository.add(existingCabinet)
            require(repository.getAll().size == 1)

            val result = StorageCabinet.decommission(existingCabinet.id, repository)

            result.shouldBeSuccess()
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

    describe("Add storage box") {
        it("adds a storage box to the storage cabinet") {
            val cabinet = TestDataFactory.createRandomDefaultStorageCabinet()
            val storageBox = TestDataFactory.createRandomDefaultStorageBox()
            require(cabinet.storageBoxes.none { it.id == storageBox.id })

            val result = cabinet.addStorageBox(storageBox)

            cabinet.storageBoxes.contains(storageBox)
            result shouldBeSuccess cabinet
        }

        it("does not add storage box when it is already added") {
            val cabinet = TestDataFactory.createRandomDefaultStorageCabinet()
            val storageBox = TestDataFactory.createRandomDefaultStorageBox()
            val otherBox = storageBox.copy(
                name = StorageBoxName("AnotherName"),
                description = StorageBoxDescription("anotherDescription")
            )
            cabinet.addStorageBox(storageBox)
            require(cabinet.storageBoxes.any { it.id == storageBox.id })

            val result = cabinet.addStorageBox(otherBox)
            result shouldBeFailure {
                it shouldBe AlreadyExists(otherBox.id.toString())
            }
        }
    }

    describe("Remove storage box") {
        it("removes storage box if it exists") {
            val cabinet = TestDataFactory.createRandomDefaultStorageCabinet()
            val storageBox = TestDataFactory.createRandomDefaultStorageBox()
            cabinet.addStorageBox(storageBox)
            require(cabinet.storageBoxes.any { it.id == storageBox.id })

            val result = cabinet.removeStorageBox(storageBox.id)

            cabinet.storageBoxes.shouldNotContain(storageBox)
            result shouldBeSuccess cabinet
        }

        it("does not remove storage box if it was never there") {
            val cabinet = TestDataFactory.createRandomDefaultStorageCabinet()
            val storageBox = TestDataFactory.createRandomDefaultStorageBox()
            val otherBox = TestDataFactory.createRandomDefaultStorageBox()
            cabinet.addStorageBox(storageBox)
            require(cabinet.storageBoxes.any { it.id == storageBox.id })

            val result = cabinet.removeStorageBox(otherBox.id)
            result shouldBeFailure {
                it shouldBe NotFound(otherBox.id.toString())
            }
            cabinet.storageBoxes shouldContain storageBox
        }
    }

})
