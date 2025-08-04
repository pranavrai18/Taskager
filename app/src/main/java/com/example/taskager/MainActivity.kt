package com.example.taskager

import Task_history
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkAndRequestPermissions()

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(false)

        drawerLayout = findViewById(R.id.sidedrawer)
        val navView = findViewById<NavigationView>(R.id.sidenavview)

        navView.setNavigationItemSelectedListener(this)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, HomeFragment()).commit()

        }

        val hamburger = toolbar.findViewById<ImageButton>(R.id.toolbar_hamburger)
        hamburger.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)

        }
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.nav_home
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_tasks -> {
                    // Show Tasks fragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frag_container, CompletedTaskFragment())
                        .commit()
                    true
                }
                R.id.nav_home -> {
                    // Show Home fragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frag_container, HomeFragment())
                        .commit()
                    true
                }
                R.id.nav_profile -> {
                    // Show Profile fragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frag_container, ProfileFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }


    }
    fun updateNavHeader(name: String, avatarUri: String?) {
        val navView = findViewById<NavigationView>(R.id.sidenavview)
        val headerView = navView.getHeaderView(0)
        val navHeaderName = headerView.findViewById<TextView>(R.id.username)
        val navHeaderAvatar = headerView.findViewById<CircleImageView>(R.id.avatar)

        navHeaderName.text = name
        if (!avatarUri.isNullOrEmpty()) {
            navHeaderAvatar.setImageURI(Uri.parse(avatarUri))
        } else {
            navHeaderAvatar.setImageResource(R.drawable.cool)
        }
    }

    private fun checkAndRequestPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        // Storage permissions (Android 12 or lower)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
            != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.READ_MEDIA_IMAGES)
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        // Notification permission (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), 100)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Handle allowed/denied responses here
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_deltask -> supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, Task_history()).commit()
            R.id.nav_tnc -> supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, TermsFragment()).commit()
            R.id.nav_logout -> { val intent = Intent(this, loginactivity::class.java)
                startActivity(intent)}
            R.id.nav_feedback -> supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, Feedback()).commit()
            R.id.nav_settings -> supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, SettingsFragment()).commit()
            R.id.nav_notifications -> supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, Notification()).commit()

        }
        drawerLayout.closeDrawers()
        true
        return true
    }



}
