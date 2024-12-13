package fr.adel.fitnessapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.adel.fitnessapp.R
import fr.adel.fitnessapp.adapters.ExerciseAdapter
import fr.adel.fitnessapp.models.ExerciseResponse
import fr.adel.fitnessapp.models.Exerciseitem
import fr.adel.fitnessapp.models.QueryRequest
import fr.adel.fitnessapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExerciseFragment : Fragment() {

    private lateinit var recyclerViewApiEX: RecyclerView
    private lateinit var recyclerViewDatabaseEX: RecyclerView
    private lateinit var apiAdapter: ExerciseAdapter
    private lateinit var databaseAdapter: ExerciseAdapter



    private lateinit var exerciseNameInput: EditText // Champ de recherche pour l'exercice
    private lateinit var searchExerciseButton: Button // Bouton de recherche pour les exercices




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercise, container, false)

        recyclerViewApiEX = view.findViewById(R.id.recyclerViewApiEX)
        recyclerViewDatabaseEX = view.findViewById(R.id.recyclerViewDatabaseEX)

        exerciseNameInput = view.findViewById(R.id.exercise_name_input) // Initialisation champ exercice
        searchExerciseButton = view.findViewById(R.id.search_exercise_button) // Initialisation bouton exercice

        recyclerViewApiEX.layoutManager = LinearLayoutManager(context)
        recyclerViewDatabaseEX.layoutManager = GridLayoutManager(context, 2)

        apiAdapter = ExerciseAdapter()
        databaseAdapter = ExerciseAdapter()

        recyclerViewApiEX.adapter = apiAdapter
        recyclerViewDatabaseEX.adapter = databaseAdapter

        // Charger les données de la base de données locale
        val localExerciseItems = loadLocalExerciseData()
        databaseAdapter.setExerciseItems(localExerciseItems)
        searchExerciseButton.setOnClickListener {
            val query = exerciseNameInput.text.toString()
            if (query.isNotBlank()) {
                fetchExerciseData(query)
            } else {
                Toast.makeText(context, "Veuillez entrer un aliment", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun fetchExerciseData(query: String) {
        val request = QueryRequest(query) // Crée cette classe pour la requête d'exercice

        val call = RetrofitClient.api.getExerciseData(request)
        call.enqueue(object : Callback<ExerciseResponse> {
            override fun onResponse(
                call: Call<ExerciseResponse>,
                response: Response<ExerciseResponse>
            ) {
                if (response.isSuccessful) {
                    val exercises = response.body()?.exercises ?: listOf()
                    apiAdapter.setExerciseItems(exercises)
                } else {
                    Toast.makeText(context, "Erreur API : ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ExerciseResponse>, t: Throwable) {
                Toast.makeText(context, "Échec de la connexion : ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadLocalExerciseData(): List<Exerciseitem> {
        val json = context?.assets?.open("exercices.json")?.bufferedReader().use { it?.readText() }
        val gson = Gson()
        return gson.fromJson(json, Array<Exerciseitem>::class.java).toList()
    }



}
