package com.example.kredit

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CalculatorActivity : AppCompatActivity() {

    private lateinit var seekAmount: SeekBar
    private lateinit var etTerm: EditText
    private lateinit var btnCalculate: Button
    private lateinit var btnBack: TextView
    private lateinit var tvAmount: TextView

    private var creditAmount = 30000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        seekAmount = findViewById(R.id.seekAmount)
        etTerm = findViewById(R.id.etTerm)
        btnCalculate = findViewById(R.id.btnCalculate)
        btnBack = findViewById(R.id.btnBack)
        tvAmount = findViewById(R.id.tvAmount)

        seekAmount.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                creditAmount = 30000 + progress * 1000
                tvAmount.text = "до $creditAmount"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnCalculate.setOnClickListener {
            val termStr = etTerm.text.toString().trim()

            if (termStr.isEmpty()) {
                Toast.makeText(this, "Введите срок кредита", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val termMonths = termStr.toInt()
            val payment = calculatePayment(creditAmount, termMonths)

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("payment", payment)
            intent.putExtra("amount", creditAmount)
            intent.putExtra("term", termMonths)
            startActivity(intent)
        }
    }

    private fun calculatePayment(amount: Int, termMonths: Int): Double {
        return if (termMonths <= 12) {
            amount.toDouble() / termMonths + amount * 0.059
        } else if (termMonths <= 24) {
            val s1 = amount.toDouble() / termMonths + amount * 0.059
            val paidIn12 = s1 * 12
            amount.toDouble() / termMonths + (amount - paidIn12) * 0.051
        } else {
            val s1 = amount.toDouble() / termMonths + amount * 0.059
            val paidIn12 = s1 * 12
            amount.toDouble() / termMonths + (amount - paidIn12) * 0.042
        }
    }
}