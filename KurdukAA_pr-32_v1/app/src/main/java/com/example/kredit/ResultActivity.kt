package com.example.kredit


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvResultAmount: TextView = findViewById(R.id.tvResultAmount)
        val tvResultTerm: TextView = findViewById(R.id.tvResultTerm)
        val tvResultPayment: TextView = findViewById(R.id.tvResultPayment)
        val btnToMain: Button = findViewById(R.id.btnToMain)

        // Получаем данные переданные с экрана 2
        val payment = intent.getDoubleExtra("payment", 0.0)
        val amount = intent.getIntExtra("amount", 0)
        val term = intent.getIntExtra("term", 0)

        tvResultAmount.text = "Сумма кредита: $amount руб."
        tvResultTerm.text = "Срок кредита: $term мес."

        // Переводим в тысячи рублей как требует задание
        val paymentInThousands = payment / 1000.0
        val paymentText = String.format("%.2f тыс. руб./мес.", paymentInThousands)
        tvResultPayment.text = "Ежемесячный платёж: $paymentText"

        // Кнопка Регистрация — переход на экран 1
        btnToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}