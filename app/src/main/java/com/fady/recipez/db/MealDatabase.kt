package com.fady.recipez.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fady.recipez.pojo.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConvertor::class)
abstract class MealDatabase:RoomDatabase() {
    abstract fun mealDao():MealDao

    companion object{
        @Volatile  //Any change on this instance from any thread will be visible to any other thread
        var INSTANCE :MealDatabase?=null

        @Synchronized   //Only one thread can have instance from roomdatabase
        fun getInstance(context:Context):MealDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context,MealDatabase::class.java,"meal.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return  INSTANCE as MealDatabase
        }
    }
}