package fr.adel.fitnessapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import fr.adel.fitnessapp.R
import fr.adel.fitnessapp.adapters.ProgrammesAdapter
import fr.adel.fitnessapp.models.Programme
import fr.adel.fitnessapp.models.ProgrammesWrapper

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Charger les données JSON (à partir d'une chaîne ou d'une ressource, par exemple)
        val json = loadJsonDataFromAssets() // Remplace par ton méthode de chargement du JSON
        val programmes = loadProgrammesFromJson(json) // Charge les données JSON

        recyclerView.adapter = ProgrammesAdapter(programmes)

        return view
    }

    private fun loadJsonDataFromAssets(): String {
        // Méthode pour charger les données JSON depuis les assets (par exemple)
        return try {
            val inputStream = requireContext().assets.open("programmes.json") // Exemple de fichier
            inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Log.e("JSON", "Erreur lors du chargement du fichier JSON", e)
            ""
        }
    }


    fun loadProgrammesFromJson(json: String): List<Programme> {
        // Afficher la chaîne JSON dans les logs avant de la parser
        Log.d("JSON", json)

        // Vérification et parsing du JSON
        return try {
            // Si le JSON est un tableau directement
            if (json.trim().startsWith("[")) {
                val programmes = Gson().fromJson<List<Programme>>(json, object : TypeToken<List<Programme>>() {}.type)
                programmes
            } else {
                // Si c'est un objet contenant un tableau, comme dans ProgrammesWrapper
                val programmesWrapper = Gson().fromJson(json, ProgrammesWrapper::class.java)
                programmesWrapper.programmes
            }
        } catch (e: JsonSyntaxException) {
            Log.e("JSON Parsing", "Erreur lors du parsing JSON", e)
            emptyList()  // Retourne une liste vide en cas d'erreur
        }
    }

}
