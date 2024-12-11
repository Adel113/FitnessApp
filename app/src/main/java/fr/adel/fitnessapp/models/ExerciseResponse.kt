package fr.adel.fitnessapp.models

data class ExerciseResponse(
    val exercises: List<Exerciseitem>
)

data class Exerciseitem(
    val user_input: String,
    val duration_min: Int,
    val nf_calories: Double,
    val photo: Photo,
    val name: String
)

data class Photo(
    val thumb: String
)
