package com.tc.restapi.data.remote

import com.tc.restapi.data.model.user.UserDataItemModel
import com.tc.restapi.data.remote.ApiDetails.USER_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET

class ApiEndpoint {

    @GET(ApiDetails.USER_ENDPOINT)
    suspend fun getUserInfo(): Response<ArrayList<UserDataItemModel>>

}