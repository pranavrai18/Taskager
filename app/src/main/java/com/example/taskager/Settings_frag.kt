package com.example.taskager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.edit

class SettingsFragment : Fragment() {

    private lateinit var darkModeSwitch: Switch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        darkModeSwitch = view.findViewById(R.id.darkModeSwitch)


        // Load saved preferences
        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        darkModeSwitch.isChecked = prefs.getBoolean("dark_mode", false)


        // Dark Mode Toggle
        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            val mode = if (isChecked)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(mode)
            prefs.edit { putBoolean("dark_mode", isChecked) }
        }



        // Language & Region Card Click
        view.findViewById<View>(R.id.editAvatarContainer)?.setOnClickListener {
            // Replace with your navigation or dialog logic
            Toast.makeText(requireContext(), "Language & Region settings", Toast.LENGTH_SHORT).show()
        }

        // About & Support Card Click
        view.findViewById<View>(R.id.saveButton)?.setOnClickListener {
            // Replace with your navigation or dialog logic
            Toast.makeText(requireContext(), "About & Support", Toast.LENGTH_SHORT).show()
        }
    }
}
