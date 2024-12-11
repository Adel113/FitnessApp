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
import fr.adel.fitnessapp.adapters.FoodAdapter
import fr.adel.fitnessapp.models.FoodItem
import fr.adel.fitnessapp.models.NutritionalResponse
import fr.adel.fitnessapp.models.QueryRequest
import fr.adel.fitnessapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodFragment : Fragment() {

    private lateinit var recyclerViewApi: RecyclerView
    private lateinit var recyclerViewDatabase: RecyclerView
    private lateinit var apiAdapter: FoodAdapter
    private lateinit var databaseAdapter: FoodAdapter

    private lateinit var foodNameInput: EditText
    private lateinit var searchButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food, container, false)

        // Initialisation des RecyclerViews et des adaptateurs
        recyclerViewApi = view.findViewById(R.id.recyclerViewApi)
        recyclerViewDatabase = view.findViewById(R.id.recyclerViewDatabase)
        foodNameInput = view.findViewById(R.id.food_name_input)
        searchButton = view.findViewById(R.id.search_button)

        recyclerViewApi.layoutManager = LinearLayoutManager(context)
        recyclerViewDatabase.layoutManager = GridLayoutManager(context, 2)

        apiAdapter = FoodAdapter()
        databaseAdapter = FoodAdapter()

        recyclerViewApi.adapter = apiAdapter
        recyclerViewDatabase.adapter = databaseAdapter

        // Recherche sur le bouton


        // Charger les données de la base de données locale
        val localFoodItems = loadLocalFoodData()
        databaseAdapter.setFoodItems(localFoodItems)
        searchButton.setOnClickListener {
            val query = foodNameInput.text.toString()
            if (query.isNotBlank()) {
                fetchFoodData(query)
            } else {
                Toast.makeText(context, "Veuillez entrer un aliment", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun fetchFoodData(query: String) {
        val request = QueryRequest(query)

        val call = RetrofitClient.api.getData(request)
        call.enqueue(object : Callback<NutritionalResponse> {
            override fun onResponse(
                call: Call<NutritionalResponse>,
                response: Response<NutritionalResponse>
            ) {
                if (response.isSuccessful) {
                    val foods = response.body()?.foods ?: listOf()
                    apiAdapter.setFoodItems(foods)
                } else {
                    Toast.makeText(context, "Erreur API : ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NutritionalResponse>, t: Throwable) {
                Toast.makeText(context, "Échec de la connexion : ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadLocalFoodData(): List<FoodItem> {
        val json = context?.assets?.open("foods.json")?.bufferedReader().use { it?.readText() }
        val gson = Gson()
        return gson.fromJson(json, Array<FoodItem>::class.java).toList()
    }
}


