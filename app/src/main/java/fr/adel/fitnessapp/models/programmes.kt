
package fr.adel.fitnessapp.models

data class ExerciceExemples(
    val nom: String,
    val series: Int?,
    val repetitions: Int?,
    val duree: String? // La durée est optionnelle, car elle peut ne pas être présente
)

data class Jour(
    val jour: String,
    val exercices: List<ExerciceExemples>
)

data class Programme(
    val nom: String,
    val description: String,
    val jours: List<Jour>
)

data class ProgrammesWrapper(
    val programmes: List<Programme>
)


