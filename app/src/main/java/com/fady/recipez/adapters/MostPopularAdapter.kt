package com.fady.recipez.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fady.recipez.databinding.PopularItemsBinding
import com.fady.recipez.pojo.MealsByCategory

class MostPopularAdapter() : RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
    lateinit var onItemClick:(MealsByCategory) -> Unit
    private var mealList = ArrayList<MealsByCategory>()

    var onLongItemClick:((MealsByCategory) -> Unit)?=null

    fun setMeals(mealsByCategoryList:ArrayList<MealsByCategory>){
        this.mealList = mealsByCategoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopularItem)

        holder.itemView.setOnClickListener {

            //onItemClick(mealList[position])
            onItemClick.invoke(mealList[position])
        }

        holder.itemView.setOnLongClickListener {
            onLongItemClick?.invoke(mealList[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    class PopularMealViewHolder( val binding:PopularItemsBinding):RecyclerView.ViewHolder(binding.root)

}