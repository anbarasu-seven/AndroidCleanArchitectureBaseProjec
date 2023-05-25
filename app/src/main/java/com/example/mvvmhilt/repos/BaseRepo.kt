package com.example.mvvmhilt.repos


import com.example.mvvmhilt.data.models.ErrorResponse
import com.example.mvvmhilt.data.models.Resource
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseRepo() {

    // we'll use this function in all
    // repos to handle api errors.
    protected suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {

        // Returning api response
        // wrapped in Resource class
        return withContext(Dispatchers.IO) {
            try {
                // Here we are calling api lambda
                // function that will return response
                // wrapped in Retrofit's Response class
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    // In case of success response we
                    // are returning Resource.Success object
                    // by passing our data in it.
                    Resource.Success(data = response.body()!!)
                } else {
                    // parsing api's own custom json error
                    // response in ExampleErrorResponse pojo
                    val errorResponse: ErrorResponse? = convertErrorBody(response.errorBody())
                    // Simply returning api's own failure message
                    Resource.Error(errorMessage = errorResponse?.failureMessage ?: "Something went wrong")
                }

            } catch (e: HttpException) {
                // Returning HttpException's message
                // wrapped in Resource.Error
                Resource.Error(errorMessage = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                // Returning no internet message
                // wrapped in Resource.Error
                Resource.Error("Please check your network connection")
            } catch (e: Exception) {
                // Returning 'Something went wrong' in case
                // of unknown error wrapped in Resource.Error
                Resource.Error(errorMessage = "Something went wrong")
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