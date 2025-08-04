//package com.example.taskager
//
//import android.content.Context
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Switch
//import androidx.appcompat.app.AppCompatDelegate
//import androidx.cardview.widget.CardView
//import androidx.fragment.app.Fragment
//import android.widget.Toast
//
//class Notification : Fragment() {
//    private lateinit var notificationsSwitch: Switch
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_notification, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//        notificationsSwitch = view.findViewById(R.id.notificationswitch)
//
//        // Load saved preferences
//        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
//
//        notificationsSwitch.isChecked = prefs.getBoolean("notifications", true)
//
//
//
//    }
//}
package com.example.taskager

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class Notification : Fragment() {
    private lateinit var notificationsSwitch: Switch

    // Modern ActivityResult API launcher
    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(requireContext(), "Notification permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Notification permission denied. You won't receive notifications.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationsSwitch = view.findViewById(R.id.notificationswitch)
        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        notificationsSwitch.isChecked = prefs.getBoolean("notifications", true)

        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("notifications", isChecked).apply()
            Toast.makeText(
                requireContext(),
                if (isChecked) "Notifications ON" else "Notifications OFF",
                Toast.LENGTH_SHORT
            ).show()

            // Use modern permission requesting
            if (isChecked && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
