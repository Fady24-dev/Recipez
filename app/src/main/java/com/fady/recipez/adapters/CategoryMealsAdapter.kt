package com.fady.recipez.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fady.recipez.databinding.ActivityCategoryMealBinding
import com.fady.recipez.databinding.MealItemBinding
import com.fady.recipez.pojo.Meal
import com.fady.recipez.pojo.MealsByCategory
import com.fady.recipez.pojo.MealsByCategoryList

class CategoryMealsAdapter:RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {

    private var mealList =ArrayList<MealsByCategory>()
    var onItemClick : ((MealsByCategory) -> Unit)?=null

        fun setMealsList(mealList:List<MealsByCategory>){
            this.mealList = mealList as ArrayList<MealsByCategory>
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealList[position].strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealList[position].strMeal

        holder.itemView.setOnClickListener {
            //onItemClick(mealList[position])
            onItemClick!!.invoke(mealList[position])
        }


    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    inner class CategoryMealsViewHolder(val binding: MealItemBinding):RecyclerView.ViewHolder(binding.root){

    }

}