package dev.bober.store.data.utils

/**
 * Класс, для обработки состояния отправки запроса в сеть
 * @property data данные, полученные из сети
 */
sealed class Resource<out T> {
    abstract val data: T?

    data class Success<T>(override val data: T) : Resource<T>()
    data class Loading<T>(override val data: T? = null) : Resource<T>()
    data class Error<T>(val code: Int? = null, val error: Throwable? = null, override val data: T? = null) : Resource<T>()
}