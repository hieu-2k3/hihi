package com.example.demonews

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.demonews.activities.ListNewActivity
import com.example.demonews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()
    }

    private fun setUpView(){
        binding.btnAppleNews.setOnClickListener {
            val intent = Intent(this, ListNewActivity::class.java)
            intent.putExtra("type", "technology")
            startActivity(intent)
        }

        binding.btnTopBussinessNews.setOnClickListener {
            val intent = Intent(this, ListNewActivity::class.java)
            intent.putExtra("type", "TopBussiness")
            startActivity(intent)
        }
    }
}