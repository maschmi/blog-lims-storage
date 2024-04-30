package domain

import java.util.*

// for now, we treat a room a kind of value object.
// also there may be other values coming up like floor and building to describe where the room is located
data class Room(val name: RoomName)

@JvmInline
value class RoomName(val value: String) {
    override fun toString(): String {
        return "RoomName(value='$value')"
    }
}