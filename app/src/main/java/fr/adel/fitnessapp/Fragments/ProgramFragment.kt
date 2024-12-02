package fr.adel.fitnessapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.adel.fitnessapp.R
import fr.adel.fitnessapp.R.id.search_button
import fr.adel.fitnessapp.adapters.FoodAdapter
import fr.adel.fitnessapp.models.FoodItem
import fr.adel.fitnessapp.models.NutritionalResponse
import fr.adel.fitnessapp.models.QueryRequest
import fr.adel.fitnessapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProgramFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FoodAdapter
    private lateinit var foodNameInput: EditText  // Déclarer la vue EditText
    private lateinit var searchButton: Button  // Déclarer la vue Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_program, container, false)

        // Associer les vues
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        foodNameInput = view.findViewById(R.id.food_name_input)  // Associer EditText
        searchButton = view.findViewById(search_button)  // Associer Button

        adapter = FoodAdapter()
        recyclerView.adapter = adapter

        searchButton.setOnClickListener {
            val foodName = foodNameInput.text.toString()
            if (foodName.isNotBlank()) {
                fetchFoodData(foodName)
            } else {
                Toast.makeText(context, "Veuillez entrer un nom d'aliment", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun fetchFoodData(foodName: String) {
        // Crée un objet QueryRequest avec la recherche souhaitée
        val queryRequest = QueryRequest(foodName)

        val call = RetrofitClient.api.getData(queryRequest)
        call.enqueue(object : Callback<NutritionalResponse> {
            override fun onResponse(
                call: Call<NutritionalResponse>,
                response: Response<NutritionalResponse>
            ) {
                if (response.isSuccessful) {
                    // Si la réponse est correcte, on met à jour l'adaptateur avec les données
                    response.body()?.foods?.let {
                        adapter.setFoodItems(it)
                    }
                } else {
                    Toast.makeText(context, "Erreur API", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NutritionalResponse>, t: Throwable) {
                // En cas d'erreur lors de l'appel à l'API
                Toast.makeText(context, "Échec: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
