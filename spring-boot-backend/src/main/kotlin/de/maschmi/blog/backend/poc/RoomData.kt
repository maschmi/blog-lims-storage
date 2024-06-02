package de.maschmi.blog.backend.poc

import domain.Room
import jakarta.persistence.Embeddable

@Embeddable
data class RoomData(val room: String)