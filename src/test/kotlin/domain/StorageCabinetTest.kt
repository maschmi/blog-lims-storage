package domain

import TestDataFactory
import copy
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
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
           val fixture = TestFixtureFactory.createStorageCabinetFixture()

            val expectedCabinet = fixture.createRandomNewCabinet()

            val newCabinet =
                StorageCabinet.commission(expectedCabinet.name, expectedCabinet.room, fixture.repository, fixture.validator)

            newCabinet shouldBeSuccess {
                it.shouldBeEqualToIgnoringFields(expectedCabinet, StorageCabinet::id)
            }

            fixture.repository.getAll() shouldContainExactly listOf(fixture.cabinet, newCabinet.getOrThrow())
        }

        it("returns already existing when cabinet with same name is already present") {
            val fixture = TestFixtureFactory.createStorageCabinetFixture()
            val duplicatedName = fixture.cabinet.name
            val room = TestDataFactory.createRandomDefaultRoom()

            val newCabinet = StorageCabinet.commission(duplicatedName, room, fixture.repository, fixture.validator)

            newCabinet shouldBeFailure {
                it shouldBeEqualUsingFields ConstraintViolation("Name already used")
            }
        }
    }

    describe("Decommission a storage cabinet") {
        it("does nothing if it was already decommissioned or never there") {
            val fixture = TestFixtureFactory.createStorageCabinetFixture()
            val repository = fixture.repository

            val idOfNotExistingCabinet = StorageCabinetId.new()
            require(repository.getAll().size == 1)

            val result = StorageCabinet.decommission(idOfNotExistingCabinet, repository)
            result.shouldBeFailure(NotFound(idOfNotExistingCabinet.toString()))

            repository.getAll() shouldContainExactly listOf(fixture.cabinet)
        }

        it("returns InUseError when Storage Cabinet is not empty") {
            val fixture = TestFixtureFactory.createStorageCabinetFixture()
            val repository = fixture.repository
            val cabinetId = fixture.cabinet.id

            require(repository.getAll().size == 1)

            val result = StorageCabinet.decommission(cabinetId, repository)

            result.shouldBeFailure(InUseException(cabinetId.toString()))
            repository.getAll() shouldContainExactly listOf(fixture.cabinet)
        }

        it("removes empty StorageCabinet from repository") {
            val fixture = TestFixtureFactory.createStorageCabinetFixture()
            val repository = fixture.repository

            val existingCabinet = fixture.addEmptyCabinet()
            require(repository.getAll().size == 2)

            val result = StorageCabinet.decommission(existingCabinet.id, repository)

            result.shouldBeSuccess()
            repository.getAll() shouldContainExactly listOf(fixture.cabinet)
        }

    }

    describe("Update name of storage cabinet") {
        it("updates cabinet name if the name is not yet existing in the repository") {
            val fixture = TestFixtureFactory.createStorageCabinetFixture()
            fixture.addRandomCabinet()
            val existingCabinet = fixture.cabinet

            val expectedCabinet = existingCabinet.copy(name = StorageCabinetName("newName"))
            require(fixture.repository.getAll().size == 2)

            val result = existingCabinet.updateName(expectedCabinet.name, fixture.validator)

            // returns the updated cabinet
            result shouldBeSuccess {
                it shouldBeEqualUsingFields expectedCabinet
            }

            // makes sure the instance is updated
            existingCabinet shouldBeEqualUsingFields expectedCabinet
        }

        it("returns failure when name is already existing in repository") {
            val fixture = TestFixtureFactory.createStorageCabinetFixture()
            val otherCabinet = fixture.addRandomCabinet()
            val existingCabinet = fixture.cabinet

            require(fixture.repository.getAll().size == 2)

            val result = existingCabinet.updateName(otherCabinet.name, fixture.validator)

            result shouldBeFailure {
                it shouldBeEqualUsingFields ConstraintViolation("Name already used")
            }
        }
    }

    describe("Update room of storage cabinet") {
        it("updates room") {
            val fixture = TestFixtureFactory.createStorageCabinetFixture()
            val cabinet = fixture.cabinet
            val newRoom = TestDataFactory.createRandomDefaultRoom()
            require(cabinet.room != newRoom)
            val expectedCabinet = cabinet.copy(room = newRoom)

            cabinet.updateRoom(newRoom)

            cabinet shouldBeEqualToComparingFields expectedCabinet
        }
    }

    describe("Add storage box") {
        it("adds a storage box to the storage cabinet") {
            val fixture = TestFixtureFactory.createStorageCabinetFixture()
            val cabinet = fixture.cabinet
            val storageBox = TestDataFactory.createRandomDefaultStorageBox()
            require(cabinet.storageBoxes.none { it.id == storageBox.id })

            val result = cabinet.addStorageBox(storageBox)

            cabinet.storageBoxes  shouldContainExactlyInAnyOrder listOf(storageBox, fixture.boxInCabinet)
            result shouldBeSuccess cabinet
        }

        it("does not add storage box when it is already added") {
            val fixture = TestFixtureFactory.createStorageCabinetFixture()
            val cabinet = fixture.cabinet
            val storageBox = fixture.boxInCabinet
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
            val fixture = TestFixtureFactory.createStorageCabinetFixture()
            val cabinet = fixture.cabinet
            val storageBox = fixture.boxInCabinet
            require(cabinet.storageBoxes.any { it.id == storageBox.id })

            val result = cabinet.removeStorageBox(storageBox.id)

            cabinet.storageBoxes.shouldNotContain(storageBox)
            result shouldBeSuccess cabinet
        }

        it("does not remove storage box if it was never there") {
            val fixture = TestFixtureFactory.createStorageCabinetFixture()
            val cabinet = fixture.cabinet
            val otherBox = TestDataFactory.createRandomDefaultStorageBox()
            require(cabinet.storageBoxes.any { it.id == fixture.boxInCabinet.id })

            val result = cabinet.removeStorageBox(otherBox.id)
            result shouldBeFailure {
                it shouldBe NotFound(otherBox.id.toString())
            }
            cabinet.storageBoxes shouldContain fixture.boxInCabinet
        }
    }

})
