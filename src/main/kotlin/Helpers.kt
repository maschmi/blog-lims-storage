@Suppress("UNCHECKED_CAST")
fun <T, R> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> {
    return when {
        isSuccess -> transform(getOrNull()!!)
        else -> this as Result<R> // this always works as we do have a failure and failure is always a throwable
    }
}