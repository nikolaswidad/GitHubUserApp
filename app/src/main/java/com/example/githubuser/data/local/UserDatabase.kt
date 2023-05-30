package com.example.githubuser.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities =[FavoriteUser::class],
    version = 1
)
abstract class UserDatabase: RoomDatabase() {
    companion object{
        var INSTANCES : UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase?{
            if (INSTANCES==null){
                synchronized(UserDatabase::class){
                    INSTANCES = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "user database").build()
                }
            }
            return INSTANCES
        }
    }

    abstract fun favoriteUserDao(): FavoriteUserDao
}