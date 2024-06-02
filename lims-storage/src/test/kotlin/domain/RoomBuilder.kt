package domain

import generateRandomString

class RoomBuilder {

    private var roomName = RoomName("Room-${generateRandomString(10)}")

    fun withName(roomName: RoomName): RoomBuilder {
        this.roomName = roomName
        return this
    }

    fun build(): Room {
        return Room(roomName)
    }

}
