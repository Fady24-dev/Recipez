package com.fady.recipez.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.fady.recipez.activities.MainActivity
import com.fady.recipez.activities.MealActivity
import com.fady.recipez.adapters.MealsAdapter
import com.fady.recipez.databinding.FragmentFavoritesBinding
import com.fady.recipez.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar


class FavoritesFragment : Fragment() {
    private lateinit var binding:FragmentFavoritesBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var favoriteAdapter:MealsAdapter

    companion object{
        const val FAV_MEAL_ID="com.fady.recipez.fragments.idMeal"
        const val FAV_MEAL_NAME="com.fady.recipez.fragments.nameMeal"
        const val FAV_MEAL_THUMB="com.fady.recipez.fragments.thumbMeal"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =(activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorite()
        onFavoriteItemClick()


        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT)
        {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) =true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val  position = viewHolder.adapterPosition
                viewModel.deleteMeal(favoriteAdapter.differ.currentList[position])

                Snackbar.make(requireView(),"Meal Deleted,",Snackbar.LENGTH_LONG)
                    .setAction("Undo",
                        View.OnClickListener {
                            viewModel.insertMeal(favoriteAdapter.differ.currentList[position])
                        }

                        ).show()

            }

        }
        
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)

    }

    private fun prepareRecyclerView() {
        favoriteAdapter = MealsAdapter()
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = favoriteAdapter
        }
    }

    private fun observeFavorite() {
        viewModel.observeFavoriteMealLiveData().observe(requireActivity(), Observer { meals->
            favoriteAdapter.differ.submitList(meals)

        })
    }

    private fun onFavoriteItemClick() {
        favoriteAdapter.onItemClick = {
                meal ->
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(FAV_MEAL_ID,meal.idMeal)
            intent.putExtra(FAV_MEAL_NAME,meal.strMeal)
            intent.putExtra(FAV_MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }

    }



}