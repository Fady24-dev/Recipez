package com.fady.recipez.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fady.recipez.databinding.MealItemBinding
import com.fady.recipez.pojo.Meal

class MealsAdapter:RecyclerView.Adapter<MealsAdapter.FavoritesMealsViewHolder>() {

    var onItemClick : ((Meal) -> Unit)?=null
    //Improving your recycle view ( when u delete item ,
    // it will just refresh this item and u will get the rest , instead of refreshing the whole list)

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesMealsViewHolder {
        return FavoritesMealsViewHolder(
            MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: FavoritesMealsViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView).
                load(meal.strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = meal.strMeal

        holder.itemView.setOnClickListener {
            //onItemClick(mealList[position])
            onItemClick!!.invoke(meal)
        }


    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class FavoritesMealsViewHolder(val binding:MealItemBinding):RecyclerView.ViewHolder(binding.root)

}