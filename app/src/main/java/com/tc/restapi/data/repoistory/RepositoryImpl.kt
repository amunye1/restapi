package com.tc.restapi.data.repoistory

import com.tc.restapi.data.model.user.UserDataItemModel
import com.tc.restapi.data.remote.ApiEndpoint
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    val apiClient :ApiEndpoint
):Repository {
    override suspend fun getUserInfo():Response<ArrayList<UserDataItemModel>> {
        return apiClient.getUserInfo()
        }
    }