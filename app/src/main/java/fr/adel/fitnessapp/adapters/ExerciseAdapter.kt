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
        holder.bind(item)
    }

    override fun getItemCount() = exerciseItems.size

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val exerciseName: TextView = itemView.findViewById(R.id.exercise_name)
        private val exerciseCalories: TextView = itemView.findViewById(R.id.exercise_calories)
        private val exerciseDuration: TextView = itemView.findViewById(R.id.exercise_duration)
        private val exerciseImage: ImageView = itemView.findViewById(R.id.exercise_image)

        fun bind(item: Exerciseitem) {
            exerciseName.text = item.name
            exerciseCalories.text = "Calories: ${item.nf_calories} kcal"
            exerciseDuration.text = "Duration: ${item.duration_min} min"
            Glide.with(itemView.context).load(item.photo.thumb).into(exerciseImage)
        }
    }
}
