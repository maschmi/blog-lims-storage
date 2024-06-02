package de.maschmi.blog.backend.poc

import domain.Room
import domain.RoomName
import domain.StorageBox
import org.mapstruct.Mapper

@Mapper(uses = [ValueClassMapper::class])
interface StorageBoxMapper {

    fun storageBoxTodata(box: StorageBox): StorageBoxData
    fun storageBoxDataToDomain(data: StorageBoxData): StorageBox

    fun roomTodata(room: Room): RoomData
    fun roomDataToDomain(data: RoomData): Room

    fun toRoomName(value: String): RoomName = RoomName(value = value)
    fun toRoomNameData(value: RoomName): String = value.value

    fun roomToString(room: Room): String = room.name.value
    fun stringToRoom(value: RoomData): Room = Room(RoomName(value.room))
}