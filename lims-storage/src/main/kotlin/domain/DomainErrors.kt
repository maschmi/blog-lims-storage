package domain


sealed class DomainErrors : Throwable()

class AlreadyExists(val id: String) : DomainErrors() {
    override fun toString(): String {
        return "AlreadyExists(id='$id')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AlreadyExists

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}

class NotFound(val id: String) : DomainErrors() {
    override fun toString(): String {
        return "NotFound(id='$id')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NotFound

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}

class ConstraintViolation(val constraint: String) : DomainErrors() {
    override fun toString(): String {
        return "ConstraintViolation(constraint='$constraint')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConstraintViolation

        return constraint == other.constraint
    }

    override fun hashCode(): Int {
        return constraint.hashCode()
    }

}

class InUseException(val entity: String) : DomainErrors() {
    override fun toString(): String {
        return "InUseException(entity='$entity')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InUseException

        return entity == other.entity
    }

    override fun hashCode(): Int {
        return entity.hashCode()
    }

}