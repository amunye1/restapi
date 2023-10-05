package com.tc.restapi.data.repoistory

import com.tc.restapi.data.model.user.UserDataItemModel
import retrofit2.Response

interface Repository {
    suspend fun getUserInfo(): Response<ArrayList<UserDataItemModel>>

}