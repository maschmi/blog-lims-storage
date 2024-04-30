package domain


sealed class DomainErrors : Throwable()

class AlreadyExists(val id: String) : DomainErrors()
class DoesNotExist(val id: String) : DomainErrors()
class NotFound(val id: String) : DomainErrors()