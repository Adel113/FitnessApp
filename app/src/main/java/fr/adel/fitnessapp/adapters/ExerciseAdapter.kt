package fr.adel.fitnessapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.adel.fitnessapp.R
import fr.adel.fitnessapp.R.layout.item_exercises
import fr.adel.fitnessapp.models.Exerciseitem
import com.bumptech.glide.Glide




class ExerciseAdapter : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    private var exerciseItems = listOf<Exerciseitem>()

    fun setExerciseItems(items: List<Exerciseitem>) {
        exerciseItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(item_exercises, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val item = exerciseItems[position]
        holder.exerciseName.text = item.name
        holder.exerciseCalories.text = "Calories: ${item.nf_calories} kcal"
        holder.exerciseDuration.text = "Duration: ${item.duration_min} min"

        Glide.with(holder.itemView.context)
            .load(item.photo.thumb)
            .into(holder.exerciseImage)
    }

    override fun getItemCount() = exerciseItems.size

   class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val exerciseName: TextView = itemView.findViewById(R.id.exercise_name)
        val exerciseCalories: TextView = itemView.findViewById(R.id.exercise_calories)
        val exerciseDuration: TextView = itemView.findViewById(R.id.exercise_duration)
        val exerciseImage: ImageView = itemView.findViewById(R.id.exercise_image)


    }
}
