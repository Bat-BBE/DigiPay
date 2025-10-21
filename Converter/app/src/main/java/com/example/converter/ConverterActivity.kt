package com.example.converter

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class ConverterActivity : AppCompatActivity() {
    private val rates = mapOf(
        "EUR" to 3723.0,
        "GBP" to 4300.0,
        "RUB" to 42.5,
        "CNY" to 482.0,
        "JPY" to 25.9,
        "KRW" to 2.73,
        "AUD" to 2400.0,
        "CHF" to 3910.0,
        "CAD" to 2720.0,
        "SGD" to 2780.0,
        "SEK" to 329.0,
        "TRY" to 107.0,
        "HKD" to 476.0
    )
    private lateinit var flagImage: ImageView
    private lateinit var codeText: TextView
    private lateinit var amountInput: EditText
    private lateinit var convertButton: Button
    private lateinit var resultText: TextView
    private lateinit var spinner: Spinner
    private var selectedCurrency = "EUR"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convert)
        setupViews()
        setupSpinner()
        setupConvertButton()
        showInitialCurrency()
    }
    private fun setupViews() {
        flagImage = findViewById(R.id.imgFlagLarge)
        codeText = findViewById(R.id.tvCurrencyCode)
        amountInput = findViewById(R.id.etAmount)
        convertButton = findViewById(R.id.btnConvert)
        resultText = findViewById(R.id.tvResult)
        spinner = findViewById(R.id.spinnerCurrency)
    }

    private fun setupSpinner() {
        val currencyList = rates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencyList)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                selectedCurrency = currencyList[position]
                codeText.text = selectedCurrency
                updateFlag(selectedCurrency)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupConvertButton() {
        convertButton.setOnClickListener {
            val inputText = amountInput.text.toString()
            val inputValue = inputText.toDoubleOrNull()

            if (inputValue != null && inputValue > 0) {
                val result = convertToMNT(inputValue)
                resultText.text = "$result MNT"
            } else {
                Toast.makeText(this, "Зөв дүн оруулна уу!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun showInitialCurrency() {
        val initialCode = intent.getStringExtra("currencyCode") ?: "EUR"
        selectedCurrency = initialCode
        codeText.text = initialCode
        updateFlag(initialCode)
    }
    private fun convertToMNT(amount: Double): String {
        val rate = rates[selectedCurrency] ?: 0.0
        val result = amount * rate
        val formatter = DecimalFormat("#,###.##")
        return formatter.format(result)
    }
    private fun updateFlag(currency: String) {
        val flagResId = resources.getIdentifier("flag_${currency.lowercase()}", "drawable", packageName)
        if (flagResId != 0) flagImage.setImageResource(flagResId)
    }
}
