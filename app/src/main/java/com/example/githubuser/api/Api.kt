package com.example.githubuser.api

import com.example.githubuser.data.model.DetailUserResponse
import com.example.githubuser.data.model.User
import com.example.githubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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") // sudah disembunyikan coba tapi belum berhasil
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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") // sudah coba dengan interceptor tapi belum berhasil
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowersUser(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowingsUser(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}