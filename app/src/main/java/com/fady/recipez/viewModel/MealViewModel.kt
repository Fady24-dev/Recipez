package com.fady.recipez.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fady.recipez.activities.MealActivity
import com.fady.recipez.db.MealDatabase
import com.fady.recipez.pojo.Meal
import com.fady.recipez.pojo.MealList
import com.fady.recipez.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(val mealDatabase:MealDatabase) :ViewModel() {
    private var mealRecipeLiveData = MutableLiveData<Meal>()

    fun getMealRecipe (id:String){
        RetrofitInstance.api.getMealRecipe(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    mealRecipeLiveData.value= response.body()!!.meals[0]
                }
                else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MealActivity",t.message.toString())
            }

        })
    }
    fun observeMealRecipeLiveData():LiveData<Meal> {
        return mealRecipeLiveData
    }

    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }



}