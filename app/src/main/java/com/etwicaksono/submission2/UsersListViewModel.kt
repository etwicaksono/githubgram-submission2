package com.etwicaksono.submission2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersListViewModel(type: String, username: String) : ViewModel() {

    class Factory(private val type: String, private val username: String) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UsersListViewModel(type, username) as T
        }
    }

    private val _listUsers = MutableLiveData<List<ResponseUserItem>>()
    val listUser: LiveData<List<ResponseUserItem>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userData = MutableLiveData<ResponseUserDetail>()
    val userData: LiveData<ResponseUserDetail> = _userData

    companion object {
        private val TAG = UsersListViewModel::class.java.simpleName
        private val api = ApiConfig.getApiService()
    }

    init {
        when (type.lowercase()) {
            "all" -> getAllUsers()
            "detailuser" -> getUserData(username)
            "followers" -> getFollowersData(username)
            "following" -> getFollowingData(username)
        }
    }

    private fun getFollowersData(username: String) {
        _isLoading.value = true
        val client = api.getUserFollowers(username)
        client.enqueue(object : Callback<List<ResponseUserItem>> {
            override fun onResponse(
                call: Call<List<ResponseUserItem>>,
                response: Response<List<ResponseUserItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUsers.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message().toString()}")
                }
            }

            override fun onFailure(call: Call<List<ResponseUserItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    private fun getFollowingData(username: String) {
        _isLoading.value = true
        val client = api.getUserFollowers(username)
        client.enqueue(object : Callback<List<ResponseUserItem>> {
            override fun onResponse(
                call: Call<List<ResponseUserItem>>,
                response: Response<List<ResponseUserItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUsers.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message().toString()}")
                }
            }

            override fun onFailure(call: Call<List<ResponseUserItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    private fun getAllUsers() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllUsers()
        client.enqueue(object : Callback<List<ResponseUserItem>> {
            override fun onResponse(
                call: Call<List<ResponseUserItem>>,
                response: Response<List<ResponseUserItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUsers.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ResponseUserItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    private fun getUserData(username: String) {
        _isLoading.value = true
        val client = api.getUserDetail(username)
        client.enqueue(object : Callback<ResponseUserDetail> {
            override fun onResponse(
                call: Call<ResponseUserDetail>,
                response: Response<ResponseUserDetail>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userData.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailue: ${response.message().toString()}")
                }
            }

            override fun onFailure(call: Call<ResponseUserDetail>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}