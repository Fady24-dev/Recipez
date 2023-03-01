package com.fady.recipez.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fady.recipez.R
import com.fady.recipez.activities.CategoryMealActivity
import com.fady.recipez.activities.MainActivity
import com.fady.recipez.activities.MealActivity
import com.fady.recipez.adapters.CategoriesAdapter
import com.fady.recipez.adapters.MostPopularAdapter
import com.fady.recipez.databinding.FragmentHomeBinding
import com.fady.recipez.fragments.bottomsheet.MealBottomSheetFragment
import com.fady.recipez.pojo.MealsByCategory
import com.fady.recipez.pojo.Meal
import com.fady.recipez.viewModel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var randomMeal:Meal
    private lateinit var popularItemsAdapter:MostPopularAdapter
    private lateinit var categoriesAdapter:CategoriesAdapter

    companion object{
        const val MEAL_ID="com.fady.recipez.fragments.idMeal"
        const val MEAL_NAME="com.fady.recipez.fragments.nameMeal"
        const val MEAL_THUMB="com.fady.recipez.fragments.thumbMeal"

        const val CATEGORY_ID="com.fady.recipez.fragments.idMeal"
        const val CATEGORY_NAME="com.fady.recipez.fragments.nameMeal"
        const val CATEGORY_THUMB="com.fady.recipez.fragments.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =(activity as MainActivity).viewModel
        //homeMvvm=ViewModelProviders.of(this)[HomeViewModel::class.java]

        popularItemsAdapter= MostPopularAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareItemRecyclerView()

        viewModel.getRandomMeal()
        observerRandomMealLiveData()
        onRandomMealClick()

        viewModel.getPopularItems()
        observerPopularItemsLiveData()
        onPopularItemClick()

        prepareCategoriesRecyclerView()
        viewModel.getCategories()
        observerCategoriesLiveData()
        onCategoryClick()

        onPopularItemLongClick()

        onSearchIconCLick()


    }

    private fun onSearchIconCLick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onPopularItemLongClick() {
        popularItemsAdapter.onLongItemClick={meal ->
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal Info")
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->

        val intent =Intent(activity,CategoryMealActivity::class.java)
            intent.putExtra(CATEGORY_ID,category.idCategory)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            intent.putExtra(CATEGORY_THUMB,category.strCategoryThumb)
            startActivity(intent)

        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter=CategoriesAdapter()
        binding.recViewCategories.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

    private fun observerCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categoriesAdapter.setCategoryList(categories)


        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = {
            meal ->
           val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareItemRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }

    private  fun observerPopularItemsLiveData(){
        viewModel.observePopularItemsLiveDatta().observe(viewLifecycleOwner,
            {
                mealList->
                popularItemsAdapter.setMeals(mealsByCategoryList = mealList as ArrayList<MealsByCategory>)
            })
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener(){
            val intent =Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMealLiveData() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner,object :Observer<Meal>{
            override fun onChanged(t: Meal?) {
                Glide.with(this@HomeFragment)
                    .load(t!!.strMealThumb )
                    .into(binding.imgRandomMeal)
                     randomMeal = t
            }
        })
    }


}