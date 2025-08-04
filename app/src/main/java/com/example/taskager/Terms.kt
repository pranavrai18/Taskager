package com.example.taskager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class TermsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_terms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val acceptButton = view.findViewById<MaterialButton>(R.id.acceptButton)
        acceptButton.setOnClickListener {
            // Save acceptance in SharedPreferences
            val prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            prefs.edit().putBoolean("terms_accepted", true).apply()

            // Show feedback
            Toast.makeText(requireContext(), "Terms Accepted!", Toast.LENGTH_SHORT).show()

            // Navigate away: pop this fragment (you can replace with navigation logic)
            MainActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}
