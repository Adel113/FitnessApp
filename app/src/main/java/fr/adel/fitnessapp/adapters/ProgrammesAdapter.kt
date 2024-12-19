package fr.adel.fitnessapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.adel.fitnessapp.R
import fr.adel.fitnessapp.models.Programme
import fr.adel.fitnessapp.models.Jour
import fr.adel.fitnessapp.models.Exercice
import android.widget.TextView

class ProgrammesAdapter(private val programmes: List<Programme>) :
    RecyclerView.Adapter<ProgrammesAdapter.ProgrammeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgrammeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_programme, parent, false)
        return ProgrammeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgrammeViewHolder, position: Int) {
        val programme = programmes[position]
        holder.bind(programme)
    }

    override fun getItemCount(): Int {
        return programmes.size
    }

    inner class ProgrammeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomTextView: TextView = itemView.findViewById(R.id.programmeNom)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.programmeDescription)
        private val joursTextView: TextView = itemView.findViewById(R.id.joursList)

        fun bind(programme: Programme) {
            nomTextView.text = programme.nom
            descriptionTextView.text = programme.description

            // Construire une chaîne avec les jours et exercices
            val joursDetails = StringBuilder()
            for (jour in programme.jours) {
                joursDetails.append("Jour: ${jour.jour}\n")
                for (exercice in jour.exercices) {
                    joursDetails.append("  Exercice: ${exercice.nom}\n")
                    joursDetails.append("" + "    Séries: ${exercice.series}," + "\n    Répétitions: ${exercice.repetitions}")
                    if (exercice.duree != null) {
                        joursDetails.append(",\n    Durée: ${exercice.duree}")
                    }
                    joursDetails.append("\n")
                }
                joursDetails.append("\n")
            }

            joursTextView.text = joursDetails.toString()
        }
    }
}
