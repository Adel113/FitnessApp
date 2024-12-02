package fr.adel.fitnessapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.adel.fitnessapp.R
import com.google.android.material.textview.MaterialTextView
import fr.adel.fitnessapp.models.Program

class ProgramAdapter : RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>() {

    private var programList: List<Program> = listOf()

    // Méthode pour soumettre la liste de programmes à l'adaptateur
    fun submitList(programs: List<Program>) {
        programList = programs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_program, parent, false)
        return ProgramViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        val program = programList[position]
        holder.programName.text = program.name
        holder.programDescription.text = program.description
        holder.programDays.text = program.days
    }

    override fun getItemCount(): Int = programList.size

    class ProgramViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val programName: MaterialTextView = itemView.findViewById(R.id.program_name)
        val programDescription: MaterialTextView = itemView.findViewById(R.id.program_description)
        val programDays: MaterialTextView = itemView.findViewById(R.id.program_days)
    }
}
