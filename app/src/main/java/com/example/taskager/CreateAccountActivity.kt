package com.example.taskager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import java.security.AccessController.getContext

class CreateAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_account)
        val btn = findViewById<TextView>(R.id.login_text_view_btn)
        btn.setOnClickListener {startActivity(Intent(this, loginactivity::class.java)) }
        val cbtn = findViewById<MaterialButton>(R.id.create_account_btn)
        cbtn.setOnClickListener {
            val intent = Intent(this, loginactivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this,"Account Created Successfully", Toast.LENGTH_SHORT).show()
        }
    }
}