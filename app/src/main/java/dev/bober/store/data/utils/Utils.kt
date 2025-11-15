package dev.bober.store.data.utils

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

suspend inline fun <T> safeApiResponse(
    crossinline apiToBeCalled: suspend () -> Response<T>,
): Resource<T?> = withContext(Dispatchers.IO) {
    runCatching {
        val response = apiToBeCalled()
        if (response.isSuccessful) Resource.Success(data = response.body())
        else if (response.code() == 401) Resource.Error(code = 401)
        else caseResponseCode(responseCode = response.code())
    }.getOrElse { exception ->
        Log.wtf("API", exception.message)
        caseException(exception = exception)
    }
}

fun <T> caseException(exception: Throwable): Resource.Error<T> =
    when (exception) {
        is HttpException -> caseResponseCode(exception.code())
        is SocketTimeoutException -> Resource.Error(error = exception)
        is IOException -> Resource.Error(error = exception)
        else -> Resource.Error(error = UnknownError())
    }

fun <T> caseResponseCode(responseCode: Int, ): Resource.Error<T> {
    return when (responseCode) {
        in 300..308 -> Resource.Error(code = responseCode)
        400, 402, 403, in 405..417 -> Resource.Error(code = responseCode)
        404 -> Resource.Error(code = responseCode)
        in 500..505 -> Resource.Error(code = responseCode)
        else -> Resource.Error(code = responseCode)
    }
}