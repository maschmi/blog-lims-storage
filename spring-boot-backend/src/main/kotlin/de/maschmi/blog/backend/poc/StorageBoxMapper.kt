package de.maschmi.blog.backend.poc

import domain.Room
import domain.RoomName
import domain.StorageBox
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(uses = [ValueClassMapper::class])
interface StorageBoxMapper {

    fun storageBoxToData(box: StorageBox): StorageBoxData
    fun storageBoxDataToDomain(data: StorageBoxData): StorageBox

    @Mapping(target = "copy", ignore = true)
    fun roomToData(room: Room): RoomData
    @Mapping(target = "copy", ignore = true)
    fun roomDataToDomain(data: RoomData): Room
}