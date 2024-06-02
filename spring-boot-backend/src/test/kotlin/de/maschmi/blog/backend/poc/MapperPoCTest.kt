package de.maschmi.blog.backend.poc

import domain.*
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class MapperPoCTest(
    val sut: StorageBoxMapper
) : DescribeSpec({

    describe("Room mapper") {

        val domain = Room(RoomName("test"))
        val data = RoomData("test")

        it("should map Room to RoomData") {
            val result = sut.roomToData(domain)
            result shouldBe data
        }
        it("should map RoomData to Room") {
            val result = sut.roomDataToDomain(data)
            result shouldBe domain
        }

    }

    describe("StorageBox mapper ") {
        val id = UUID.randomUUID()

        val domain = StorageBox(
            StorageBoxId(id),
            StorageBoxName("name"),
            StorageBoxDescription("description"),
        )

        val data = StorageBoxData(
            id,
            "name",
            "description",
        )

        it("should map StorageBox to StorageBoxData") {
            val result = sut.storageBoxToData(domain)
            result shouldBe data
        }

        it("should map StorageBoxData to StorageBox") {
            val result = sut.storageBoxDataToDomain(data)
            result shouldBe domain
        }

    }

}) {
}