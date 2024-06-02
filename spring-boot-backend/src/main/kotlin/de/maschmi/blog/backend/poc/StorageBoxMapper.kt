package de.maschmi.blog.backend.poc

import domain.Room
import domain.StorageBox
import org.mapstruct.Mapper

@Mapper
interface StorageBoxMapper {

    fun storageBoxTodata(box: StorageBox): StorageBoxData
    fun storageBoxDataToDomain(data: StorageBoxData): StorageBox

    fun roomTodata(room: Room): RoomData
    fun roomDataToDomain(data: RoomData): Room

}