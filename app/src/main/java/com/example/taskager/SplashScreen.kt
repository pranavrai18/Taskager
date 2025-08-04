package com.example.taskager

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        val splash = findViewById<ConstraintLayout>(R.id.splash)
        val fade = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.fade_in)
        splash.startAnimation(fade)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, CreateAccountActivity::class.java))
            finish()
        },2000)
    }
}