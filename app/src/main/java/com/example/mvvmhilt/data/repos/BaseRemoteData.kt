package com.example.mvvmhilt.data.repos


import com.example.mvvmhilt.data.models.ErrorResponse
import com.example.mvvmhilt.data.models.DataState
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseRemoteData {

    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): DataState<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled.invoke()
                if (!response.isSuccessful) {
                    val errorResponse: ErrorResponse? = convertErrorBody(response.errorBody())
                    return@withContext DataState.Error(
                        message = errorResponse?.failureMessage ?: "Something went wrong"
                    )
                }

                response.body()?.let {
                    DataState.Success(data = it)
                } ?: DataState.Error(message = "Something went wrong")

            } catch (e: HttpException) {
                DataState.Error(message = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                DataState.Error("Please check your network connection")
            } catch (e: Exception) {
                DataState.Error(message = "Something went wrong")
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