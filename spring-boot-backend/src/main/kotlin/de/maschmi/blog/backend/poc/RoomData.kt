package de.maschmi.blog.backend.poc

import de.maschmi.blog.backend.mapper.Default
import domain.Room
import jakarta.persistence.Embeddable

@Embeddable
data class RoomData @Default constructor(val name: String)