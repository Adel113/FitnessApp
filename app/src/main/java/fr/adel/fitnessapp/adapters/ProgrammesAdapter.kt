package fr.adel.fitnessapp.adapters

import android.text.SpannableString
import android.text.SpannableStringBuilder
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

            val joursDetails = SpannableStringBuilder()

            for ((indexJour, jour) in programme.jours.withIndex()) {
                // Titre du jour en gras
                val jourTitle = "Jour ${indexJour + 1}: ${jour.jour}\n"
                joursDetails.append(
                    SpannableString(jourTitle).apply {
                        setSpan(android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, jourTitle.length, 0)
                    }
                )

                if (jour.exercices.isNotEmpty()) {
                    for ((indexExo, exercice) in jour.exercices.withIndex()) {
                        // Exercice avec indentations
                        val exerciceText = "   ${indexExo + 1}. Exercice: ${exercice.nom}\n"
                        val seriesText = "      Séries: ${exercice.series}\n"
                        val repetitionsText = "      Répétitions: ${exercice.repetitions}\n"
                        val dureeText = exercice.duree?.let { "      Durée: ${it} min\n" } ?: ""

                        joursDetails.append(
                            SpannableString(exerciceText).apply {
                                setSpan(android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, exerciceText.length, 0)
                            }
                        )
                        joursDetails.append(seriesText)
                        joursDetails.append(repetitionsText)
                        joursDetails.append(dureeText)
                    }
                } else {
                    // Texte rouge si aucun exercice
                    val noExerciceText = "   Aucun exercice pour ce jour.\n"
                    joursDetails.append(
                        SpannableString(noExerciceText).apply {
                            setSpan(android.text.style.ForegroundColorSpan(android.graphics.Color.RED), 0, noExerciceText.length, 0)
                        }
                    )
                }

                joursDetails.append("\n") // Espacement entre les jours
            }

            joursTextView.text = joursDetails
        }

    }
}
