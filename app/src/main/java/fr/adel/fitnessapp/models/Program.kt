package fr.adel.fitnessapp.models

data class Program(
    val name: String = "",
    val description: String = "",
    val days: String = "",
    val createdAt: Long = 0L // Date de création du programme, par exemple pour l'ordre de tri
)
