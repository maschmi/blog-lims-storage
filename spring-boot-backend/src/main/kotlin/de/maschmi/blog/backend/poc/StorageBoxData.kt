package de.maschmi.blog.backend.poc

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "storage_boxes")
class StorageBoxData(id: String, name: String, description: String) {

    @Id
    val id: String = id

    @Column(name = "name")
    var name: String = name
        private set

    @Column(name = "description")
    var description: String = description
        private set

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StorageBoxData

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "StorageBoxData(id='$id', name='$name', description='$description')"
    }
}

