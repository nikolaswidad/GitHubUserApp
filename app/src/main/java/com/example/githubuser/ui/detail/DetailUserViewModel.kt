package com.example.githubuser.ui.detail

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuser.api.RetrofitClient
import com.example.githubuser.data.local.FavoriteUser
import com.example.githubuser.data.local.FavoriteUserDao
import com.example.githubuser.data.local.UserDatabase
import com.example.githubuser.data.model.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application): AndroidViewModel(application){
    val user = MutableLiveData<DetailUserResponse>()

    private var userDao: FavoriteUserDao?
    private var userDB: UserDatabase?

    init {
        userDB = UserDatabase.getDatabase(application)
        userDao = userDB?.favoriteUserDao()
    }

    fun setUserDetail(username: String){
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse>{
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getUserDetail() : LiveData<DetailUserResponse>{
        return user
    }

    fun addFavorite(username: String, id: Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(
                username,
                id,
                avatarUrl
            )
            userDao?.addFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFavorite(id)
        }
    }
}