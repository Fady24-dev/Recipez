package com.fady.recipez.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.fady.recipez.R
import com.fady.recipez.activities.CategoryMealActivity
import com.fady.recipez.activities.MainActivity
import com.fady.recipez.activities.MealActivity
import com.fady.recipez.adapters.CategoriesAdapter
import com.fady.recipez.databinding.FragmentCategoryBinding
import com.fady.recipez.viewModel.HomeViewModel


class CategoryFragment : Fragment() {

    private lateinit var binding:FragmentCategoryBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var viewmodel:HomeViewModel

    companion object{
        const val CAT_MEAL_ID="com.fady.recipez.fragments.idMeal"
        const val CAT_MEAL_NAME="com.fady.recipez.fragments.nameMeal"
        const val CAT_MEAL_THUMB="com.fady.recipez.fragments.thumbMeal"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeCategories()
        onCategoriesClick()
    }

    private fun onCategoriesClick() {
        categoriesAdapter.onItemClick = {
                category ->
            val intent= Intent(activity, CategoryMealActivity::class.java)
            intent.putExtra(CAT_MEAL_ID,category.idCategory)
            intent.putExtra(CAT_MEAL_NAME,category.strCategory)
            intent.putExtra(CAT_MEAL_THUMB,category.strCategoryThumb)
            startActivity(intent)
        }
    }

    private fun observeCategories() {
        viewmodel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun prepareRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoriesAdapter
        }
    }
}