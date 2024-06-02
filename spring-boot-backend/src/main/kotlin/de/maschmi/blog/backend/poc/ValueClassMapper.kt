package de.maschmi.blog.backend.poc

import domain.RoomName
import domain.StorageBoxDescription
import domain.StorageBoxId
import domain.StorageBoxName
import org.mapstruct.Mapper
import org.springframework.stereotype.Component
import java.util.*

@Component
object ValueClassMapper {

    fun toStorageBoxId(value: UUID): StorageBoxId = StorageBoxId(value = value)
    fun toStorageBoxIdValue(value: StorageBoxId): UUID = value.value
    fun toStorageBoxName(value: String): StorageBoxName = StorageBoxName(value = value)
    fun toStorageBoxNameValue(value: StorageBoxName): String = value.value
    fun toStorageBoxDescription(value: String): StorageBoxDescription = StorageBoxDescription(value = value)
    fun toStorageBoxDescriptionValue(value: StorageBoxDescription): String = value.value

    fun toRoomName(value: String): RoomName = RoomName(value = value)
    fun toRoomNameData(value: RoomName): String = value.value

}
