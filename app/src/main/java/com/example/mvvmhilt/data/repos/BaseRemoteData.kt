package com.example.mvvmhilt.data.repos


import com.example.mvvmhilt.data.models.ErrorResponse
import com.example.mvvmhilt.data.models.UiState
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseRemoteData {

    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): UiState<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled.invoke()
                if (!response.isSuccessful) {
                    val errorResponse: ErrorResponse? = convertErrorBody(response.errorBody())
                    return@withContext UiState.Error(
                        message = errorResponse?.failureMessage ?: "Something went wrong"
                    )
                }

                response.body()?.let {
                    UiState.Success(data = it)
                } ?: UiState.Error(message = "Something went wrong")

            } catch (e: HttpException) {
                UiState.Error(message = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                UiState.Error("Please check your network connection")
            } catch (e: Exception) {
                UiState.Error(message = "Something went wrong")
            }
        }
    }

    // If you don't wanna handle api's own
    // custom error response then ignore this function
    protected fun convertErrorBody(errorBody: ResponseBody?): ErrorResponse? {
        return try {
            errorBody?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }
}