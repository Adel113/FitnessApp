package fr.adel.fitnessapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import fr.adel.fitnessapp.R
import com.google.android.material.button.MaterialButton
import fr.adel.fitnessapp.adapters.ProgramAdapter
import fr.adel.fitnessapp.models.Program

class CreateProgramFragment : Fragment() {

    private lateinit var programNameLayout: EditText
    private lateinit var programDescriptionLayout: EditText
    private lateinit var programDaysLayout: EditText
    private lateinit var btnSaveProgram: MaterialButton
    private lateinit var recyclerViewPrograms: RecyclerView

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var programAdapter: ProgramAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_program, container, false)

        // Initialisation des vues
        programNameLayout = view.findViewById(R.id.program_name_input)
        programDescriptionLayout = view.findViewById(R.id.program_description_input)
        programDaysLayout = view.findViewById(R.id.program_days_input)
        btnSaveProgram = view.findViewById(R.id.btn_save_program)
        recyclerViewPrograms = view.findViewById(R.id.recycler_view_programs)

        // Initialisation Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Setup du RecyclerView
        recyclerViewPrograms.layoutManager = LinearLayoutManager(requireContext())
        programAdapter = ProgramAdapter()
        recyclerViewPrograms.adapter = programAdapter

        // Charger les programmes existants
        loadPrograms()

        // Enregistrer un nouveau programme
        btnSaveProgram.setOnClickListener {
            saveProgramToDatabase()
        }

        return view
    }

    private fun saveProgramToDatabase() {
        // Récupérer les données saisies
        val programName = programNameLayout.text.toString()
        val programDescription = programDescriptionLayout.text.toString()
        val programDays = programDaysLayout.text.toString()

        // Valider les données
        if (programName.isEmpty()) {
            programNameLayout.error = "Le nom est requis"
            return
        }
        if (programDays.isEmpty()) {
            programDaysLayout.error = "Les jours sont requis"
            return
        }

        // Obtenir l'ID de l'utilisateur connecté
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(requireContext(), "Utilisateur non connecté", Toast.LENGTH_SHORT).show()
            return
        }

        // Préparer les données à enregistrer
        val programData = hashMapOf(
            "name" to programName,
            "description" to programDescription,
            "days" to programDays,
            "createdAt" to System.currentTimeMillis()
        )

        // Enregistrer dans Firestore sous le document de l'utilisateur
        firestore.collection("users")
            .document(currentUser.uid)
            .collection("programs")
            .add(programData)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Programme enregistré avec succès", Toast.LENGTH_SHORT).show()
                clearFields()
                loadPrograms()  // Recharger les programmes après l'ajout
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Échec de l'enregistrement", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadPrograms() {
        // Obtenir l'ID de l'utilisateur connecté
        val currentUser = auth.currentUser
        if (currentUser != null) {
            firestore.collection("users")
                .document(currentUser.uid)
                .collection("programs")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    // Passer les programmes à l'adaptateur
                    val programsList = querySnapshot.toObjects(Program::class.java)
                    programAdapter.submitList(programsList)
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Erreur de chargement des programmes", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun clearFields() {
        programNameLayout.text?.clear()
        programDescriptionLayout.text?.clear()
        programDaysLayout.text?.clear()
    }
}
