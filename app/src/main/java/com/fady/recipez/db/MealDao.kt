package com.fady.recipez.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fady.recipez.pojo.Meal

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)  //If you added a meal that already exists, it will be repalced
    suspend fun upsert(meal:Meal)

//    @Update
//     suspend fun updateMeal(meal:Meal)

     @Delete
     suspend fun delete(meal:Meal)

     @Query("SELECT * FROM mealsInformation")
     fun getAllMeals():LiveData<List<Meal>>


}
