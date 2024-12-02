package fr.adel.fitnessapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.adel.fitnessapp.R

class ProgressFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Relie le layout XML du fragment
        return inflater.inflate(R.layout.fragment_progress, container, false)
    }
}