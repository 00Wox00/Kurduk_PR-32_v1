package com.example.kredit

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etLogin: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etLogin = findViewById(R.id.etLogin)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val login = etLogin.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (login.isEmpty() || password.isEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle("Ошибка")
                    .setMessage("Введите логин и пароль")
                    .setPositiveButton("OK", null)
                    .show()
                return@setOnClickListener
            }

            val prefs: SharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
            val savedLogin = prefs.getString("login", null)

            if (savedLogin == null) {
                // Первый вход — сохраняем что ввёл пользователь
                prefs.edit()
                    .putString("login", login)
                    .putString("password", password)
                    .apply()
                goToCalculator()
            } else {
                // Повторный вход — сверяем с сохранёнными
                val savedPassword = prefs.getString("password", "")
                if (login == savedLogin && password == savedPassword) {
                    goToCalculator()
                } else {
                    Toast.makeText(this, "Неверный логин или пароль. Сохранено: $savedLogin / $savedPassword", Toast.LENGTH_LONG).show()                }
            }
        }
    }

    private fun goToCalculator() {
        val intent = Intent(this, CalculatorActivity::class.java)
        startActivity(intent)
    }
}