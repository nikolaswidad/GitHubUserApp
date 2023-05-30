package com.example.githubuser.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.githubuser.api.RetrofitClient
import com.example.githubuser.data.local.SettingPreferences
import com.example.githubuser.data.model.User
import com.example.githubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel
    (private val pref: SettingPreferences)
    : ViewModel() {

    val listUser = MutableLiveData<ArrayList<User>>()

    fun getThemeSettings() = pref.getThemeSetting().asLiveData()

    fun setSearchUser(query: String){
        RetrofitClient.apiInstance
            .getSearchUser(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful){
                        listUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            })
    }

    fun getSearchUser() : LiveData<ArrayList<User>> {
        return listUser
    }

    class Factory(private val pref: SettingPreferences) :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(pref) as T
    }
}