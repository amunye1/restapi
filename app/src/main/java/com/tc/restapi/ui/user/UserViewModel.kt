package com.tc.restapi.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tc.restapi.data.model.user.UserDataItemModel
import com.tc.restapi.data.repoistory.Repository
import com.tc.restapi.util.ResponseHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    // MutableLiveData to hold the person details.
    private val _userDetails = MutableLiveData<ResponseHandling>()
    val userDetails: LiveData<ResponseHandling> = _userDetails

    // Function to fetch person details using a coroutine.
    fun getUserDetails() {
        viewModelScope.launch {

            // Make an API request to get people information.
            _userDetails.postValue(ResponseHandling.Loading)

            val result = repository.getUserInfo()

            // Update the _personDetails LiveData based on the API response.
            if (result.isSuccessful) {
                _userDetails.postValue(
                    ResponseHandling.Success(
                        result.body() ?: arrayListOf<UserDataItemModel>()
                    )
                )
            } else {
                _userDetails.postValue(ResponseHandling.Failure(result.message()))
            }
        }
    }


}