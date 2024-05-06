package domain


sealed class DomainErrors : Throwable()

class AlreadyExists(val id: String) : DomainErrors() {
    override fun toString(): String {
        return "AlreadyExists(id='$id')"
    }
}

class NotFound(val id: String) : DomainErrors() {
    override fun toString(): String {
        return "NotFound(id='$id')"
    }
}

class ConstraintViolation(val constraint: String) : DomainErrors() {
    override fun toString(): String {
        return "ConstraintViolation(constraint='$constraint')"
    }
}

class InUseException(val entity: String) : DomainErrors() {
    override fun toString(): String {
        return "InUseException(entity='$entity')"
    }
}