package com.fady.recipez.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.GeneratedAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.fady.recipez.R
import com.fady.recipez.adapters.CategoryMealsAdapter
import com.fady.recipez.databinding.ActivityCategoryMealBinding
import com.fady.recipez.fragments.FavoritesFragment
import com.fady.recipez.fragments.HomeFragment
import com.fady.recipez.viewModel.CategoryMealsViewModel

class CategoryMealActivity : AppCompatActivity() {
    lateinit var binding:ActivityCategoryMealBinding
    lateinit var categoryMealsViewModel:CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    companion object{
        const val CAT_MEAL_ID="com.fady.recipez.fragments.idMeal"
        const val CAT_MEAL_NAME="com.fady.recipez.fragments.nameMeal"
        const val CAT_MEAL_THUMB="com.fady.recipez.fragments.thumbMeal"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCategoryMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMealsViewModel=ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer {mealsList->
            binding.tvCategoryCount.text = mealsList.size.toString()

            categoryMealsAdapter.setMealsList(mealsList)


        })
        onCatItemClick()
    }

    private fun onCatItemClick() {
        categoryMealsAdapter.onItemClick = {
                meal ->
            val intent= Intent(this,MealActivity::class.java)
            intent.putExtra(CAT_MEAL_ID,meal.idMeal)
            intent.putExtra(CAT_MEAL_NAME,meal.strMeal)
            intent.putExtra(CAT_MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareRecyclerView() {
    categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter =categoryMealsAdapter
        }
    }
}