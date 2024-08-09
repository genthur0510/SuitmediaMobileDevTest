package com.genthur.suitmediatest.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.genthur.suitmediatest.R
import com.genthur.suitmediatest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        binding.btnNext.setOnClickListener {
            val name = binding.edtName.text.toString()
            if (name.isNotEmpty()) {
                saveNameToPreferences(name)
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                intent.putExtra("EXTRA_NAME", name)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter your name!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCheck.setOnClickListener {
            val inputText = binding.edtPalindrome.text.toString().replace("\\s".toRegex(), "")
            if (inputText.isNotEmpty()) {
                if (isPalindrome(inputText)) {
                    Toast.makeText(this, "is Palindrome", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "not Palindrome", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter a text!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isPalindrome(text: String): Boolean {
        val reversedText = text.reversed()
        return text.equals(reversedText, ignoreCase = true)
    }

    private fun saveNameToPreferences(name: String) {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.apply()
    }
}