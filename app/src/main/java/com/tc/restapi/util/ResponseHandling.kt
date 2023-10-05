package com.tc.restapi.util

sealed class ResponseHandling {
    object Loading : ResponseHandling()
    class Success<T>(val result: T) : ResponseHandling()
    class Failure(val error: String = "Something went wrong!") : ResponseHandling()
}
