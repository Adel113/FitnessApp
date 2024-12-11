package fr.adel.fitnessapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.adel.fitnessapp.R
import fr.adel.fitnessapp.models.FoodItem

class FoodAdapter : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private var foodItems: List<FoodItem> = listOf()

    fun setFoodItems(items: List<FoodItem>) {
        foodItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodItem = foodItems[position]
        holder.foodName.text = foodItem.food_name
        holder.calories.text = "Calories : ${foodItem.nf_calories}"
        holder.protein.text = "Protéines : ${foodItem.nf_protein} g"
        holder.Carbohydrate.text = "Carbohydrate : ${foodItem.nf_total_carbohydrate}"

        Glide.with(holder.itemView.context)
            .load(foodItem.photo.thumb)
            .into(holder.foodImage)
    }

    override fun getItemCount(): Int = foodItems.size

    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foodName: TextView = view.findViewById(R.id.food_name)
        val calories: TextView = view.findViewById(R.id.calories)
        val protein: TextView = view.findViewById(R.id.protein)
        val Carbohydrate: TextView = view.findViewById(R.id.Carbohydrate)
        val foodImage: ImageView = view.findViewById(R.id.food_image)
    }
}
