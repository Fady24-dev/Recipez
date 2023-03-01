package com.fady.recipez.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.fady.recipez.R
import com.fady.recipez.databinding.ActivityMainBinding
import com.fady.recipez.databinding.ActivityMealBinding
import com.fady.recipez.databinding.FragmentHomeBinding
import com.fady.recipez.db.MealDatabase
import com.fady.recipez.fragments.HomeFragment
import com.fady.recipez.pojo.Meal
import com.fady.recipez.viewModel.MealViewModel
import com.fady.recipez.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var youtubeLink:String
    private lateinit var mealThumb:String
    private lateinit var mealMvvm : MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase=MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm= ViewModelProvider(this,viewModelFactory).get(MealViewModel::class.java)


        getMealInfromationFromIntent()
        setInformationInViews()

        loadingCase()
        mealMvvm.getMealRecipe(mealId)
        observerMealRecipeLiveData()

        onYoutubeImgClick()
        onFavoriteClick()



    }

    private fun onFavoriteClick() {
        binding.btnFavorite.setOnClickListener{
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this,"Meal Saved",Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun onYoutubeImgClick() {
        binding.imgYoutube.setOnClickListener(){
            val intent = Intent(Intent.ACTION_VIEW,Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private  var mealToSave:Meal?=null
    private fun observerMealRecipeLiveData() {
     mealMvvm.observeMealRecipeLiveData().observe(this,object :Observer<Meal>{
         override fun onChanged(t: Meal?) {
             onResponseCase()
             val meal = t
             mealToSave=meal
             binding.tvCategory.text= "Category : ${meal!!.strCategory}"
             binding.tvArea.text = "Area : ${meal!!.strArea}"
             binding.recipeSteps.text=meal.strInstructions
             youtubeLink = t.strYoutube.toString()

         }

     })
    }

    private fun setInformationInViews() {
        Glide.with(this).load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title =mealName
    }

    private fun getMealInfromationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName=intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb=intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnFavorite.visibility = View.INVISIBLE
        binding.tvRecipe.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }
    private fun onResponseCase (){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnFavorite.visibility = View.VISIBLE
        binding.tvRecipe.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }

}