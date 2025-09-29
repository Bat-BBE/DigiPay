package com.example.unitconverter

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ConverterActivity : AppCompatActivity() {

    private lateinit var spFrom: Spinner
    private lateinit var spTo: Spinner
    private lateinit var etInput: EditText
    private lateinit var etOutput: EditText
    private lateinit var btnConvert: Button
    private lateinit var tvCategory: TextView
    private lateinit var btnBack: ImageButton

    private var category: String = "Length"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_converter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupCategory()
        setupConvertButton()
        setupBackButton()
    }

    private fun initViews() {
        spFrom = findViewById(R.id.spFrom)
        spTo = findViewById(R.id.spTo)
        etInput = findViewById(R.id.etInput)
        etOutput = findViewById(R.id.etOutput)
        btnConvert = findViewById(R.id.btnConvert)
        tvCategory = findViewById(R.id.tvCategory)
        btnBack = findViewById(R.id.btnBack)
    }

    private fun setupCategory() {
        category = intent.getStringExtra("CATEGORY") ?: "Length"
        tvCategory.text = category

        val units = when(category) {
            "Length" -> arrayOf("Meter", "Kilometer", "Centimeter", "Millimeter", "Mile", "Yard", "Foot", "Inch")
            "Area" -> arrayOf("Square Meter", "Square Kilometer", "Square Centimeter", "Square Mile", "Acre", "Hectare")
            "Volume" -> arrayOf("Liter", "Milliliter", "Cubic Meter", "Cubic Centimeter", "Gallon", "Quart")
            "Mass" -> arrayOf("Kilogram", "Gram", "Milligram", "Pound", "Ounce")
            "Time" -> arrayOf("Second", "Minute", "Hour", "Day")
            "Speed" -> arrayOf("m/s", "km/h", "mile/h", "foot/s")
            "Temperature" -> arrayOf("Celsius", "Fahrenheit", "Kelvin")
            "Density" -> arrayOf("kg/m³", "g/cm³", "lb/ft³")
            "Energy" -> arrayOf("Joule", "Kilojoule", "Calorie", "Kilocalorie")
            "Angle" -> arrayOf("Degree", "Radian", "Gradian")
            else -> arrayOf("Unit")
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spFrom.adapter = adapter
        spTo.adapter = adapter
    }

    private fun setupConvertButton() {
        btnConvert.setOnClickListener {
            val inputText = etInput.text.toString()
            if (inputText.isEmpty()) {
                Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inputValue = inputText.toDoubleOrNull()
            if (inputValue == null) {
                Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fromUnit = spFrom.selectedItem.toString()
            val toUnit = spTo.selectedItem.toString()
            val result = convertValue(inputValue, fromUnit, toUnit, category)
            etOutput.setText(result.toString())
        }
    }

    private fun setupBackButton() {
        btnBack.setOnClickListener { finish() }
    }

    // --------------------------
    // Conversion function
    // --------------------------
    private fun convertValue(value: Double, from: String, to: String, category: String): Double {
        return when(category) {
            "Length" -> convertLength(value, from, to)
            "Area" -> convertArea(value, from, to)
            "Volume" -> convertVolume(value, from, to)
            "Mass" -> convertMass(value, from, to)
            "Time" -> convertTime(value, from, to)
            "Speed" -> convertSpeed(value, from, to)
            "Temperature" -> convertTemperature(value, from, to)
            "Density" -> convertDensity(value, from, to)
            "Energy" -> convertEnergy(value, from, to)
            "Angle" -> convertAngle(value, from, to)
            else -> value
        }
    }

    // --------------------------
    // Example: Length
    // --------------------------
    private fun convertLength(value: Double, from: String, to: String): Double {
        val valueInMeters = when (from) {
            "Meter" -> value
            "Kilometer" -> value * 1000
            "Centimeter" -> value / 100
            "Millimeter" -> value / 1000
            "Mile" -> value * 1609.34
            "Yard" -> value * 0.9144
            "Foot" -> value * 0.3048
            "Inch" -> value * 0.0254
            else -> value
        }
        return when(to) {
            "Meter" -> valueInMeters
            "Kilometer" -> valueInMeters / 1000
            "Centimeter" -> valueInMeters * 100
            "Millimeter" -> valueInMeters * 1000
            "Mile" -> valueInMeters / 1609.34
            "Yard" -> valueInMeters / 0.9144
            "Foot" -> valueInMeters / 0.3048
            "Inch" -> valueInMeters / 0.0254
            else -> valueInMeters
        }
    }

    // --------------------------
    // Area
    // --------------------------
    private fun convertArea(value: Double, from: String, to: String): Double {
        val valueInM2 = when(from) {
            "Square Meter" -> value
            "Square Kilometer" -> value * 1_000_000
            "Square Centimeter" -> value / 10_000
            "Square Mile" -> value * 2_589_988.11
            "Acre" -> value * 4046.86
            "Hectare" -> value * 10_000
            else -> value
        }
        return when(to) {
            "Square Meter" -> valueInM2
            "Square Kilometer" -> valueInM2 / 1_000_000
            "Square Centimeter" -> valueInM2 * 10_000
            "Square Mile" -> valueInM2 / 2_589_988.11
            "Acre" -> valueInM2 / 4046.86
            "Hectare" -> valueInM2 / 10_000
            else -> valueInM2
        }
    }

    // --------------------------
    // Volume
    // --------------------------
    private fun convertVolume(value: Double, from: String, to: String): Double {
        val valueInL = when(from) {
            "Liter" -> value
            "Milliliter" -> value / 1000
            "Cubic Meter" -> value * 1000
            "Cubic Centimeter" -> value / 1000
            "Gallon" -> value * 3.78541
            "Quart" -> value * 0.946353
            else -> value
        }
        return when(to) {
            "Liter" -> valueInL
            "Milliliter" -> valueInL * 1000
            "Cubic Meter" -> valueInL / 1000
            "Cubic Centimeter" -> valueInL * 1000
            "Gallon" -> valueInL / 3.78541
            "Quart" -> valueInL / 0.946353
            else -> valueInL
        }
    }

    // --------------------------
    // Mass
    // --------------------------
    private fun convertMass(value: Double, from: String, to: String): Double {
        val valueInKg = when(from) {
            "Kilogram" -> value
            "Gram" -> value / 1000
            "Milligram" -> value / 1_000_000
            "Pound" -> value * 0.453592
            "Ounce" -> value * 0.0283495
            else -> value
        }
        return when(to) {
            "Kilogram" -> valueInKg
            "Gram" -> valueInKg * 1000
            "Milligram" -> valueInKg * 1_000_000
            "Pound" -> valueInKg / 0.453592
            "Ounce" -> valueInKg / 0.0283495
            else -> valueInKg
        }
    }

    // --------------------------
    // Time
    // --------------------------
    private fun convertTime(value: Double, from: String, to: String): Double {
        val valueInSec = when(from) {
            "Second" -> value
            "Minute" -> value * 60
            "Hour" -> value * 3600
            "Day" -> value * 86400
            else -> value
        }
        return when(to) {
            "Second" -> valueInSec
            "Minute" -> valueInSec / 60
            "Hour" -> valueInSec / 3600
            "Day" -> valueInSec / 86400
            else -> valueInSec
        }
    }

    // --------------------------
    // Speed
    // --------------------------
    private fun convertSpeed(value: Double, from: String, to: String): Double {
        val valueInMps = when(from) {
            "m/s" -> value
            "km/h" -> value / 3.6
            "mile/h" -> value * 0.44704
            "foot/s" -> value * 0.3048
            else -> value
        }
        return when(to) {
            "m/s" -> valueInMps
            "km/h" -> valueInMps * 3.6
            "mile/h" -> valueInMps / 0.44704
            "foot/s" -> valueInMps / 0.3048
            else -> valueInMps
        }
    }

    // --------------------------
    // Temperature
    // --------------------------
    private fun convertTemperature(value: Double, from: String, to: String): Double {
        val valueInC = when(from) {
            "Celsius" -> value
            "Fahrenheit" -> (value - 32) * 5/9
            "Kelvin" -> value - 273.15
            else -> value
        }
        return when(to) {
            "Celsius" -> valueInC
            "Fahrenheit" -> valueInC * 9/5 + 32
            "Kelvin" -> valueInC + 273.15
            else -> valueInC
        }
    }

    // --------------------------
    // Density
    // --------------------------
    private fun convertDensity(value: Double, from: String, to: String): Double {
        val valueInKgM3 = when(from) {
            "kg/m³" -> value
            "g/cm³" -> value * 1000
            "lb/ft³" -> value * 16.0185
            else -> value
        }
        return when(to) {
            "kg/m³" -> valueInKgM3
            "g/cm³" -> valueInKgM3 / 1000
            "lb/ft³" -> valueInKgM3 / 16.0185
            else -> valueInKgM3
        }
    }

    // --------------------------
    // Energy
    // --------------------------
    private fun convertEnergy(value: Double, from: String, to: String): Double {
        val valueInJ = when(from) {
            "Joule" -> value
            "Kilojoule" -> value * 1000
            "Calorie" -> value * 4.184
            "Kilocalorie" -> value * 4184
            else -> value
        }
        return when(to) {
            "Joule" -> valueInJ
            "Kilojoule" -> valueInJ / 1000
            "Calorie" -> valueInJ / 4.184
            "Kilocalorie" -> valueInJ / 4184
            else -> valueInJ
        }
    }

    // --------------------------
    // Angle
    // --------------------------
    private fun convertAngle(value: Double, from: String, to: String): Double {
        val valueInDeg = when(from) {
            "Degree" -> value
            "Radian" -> Math.toDegrees(value)
            "Gradian" -> value * 0.9
            else -> value
        }
        return when(to) {
            "Degree" -> valueInDeg
            "Radian" -> Math.toRadians(valueInDeg)
            "Gradian" -> valueInDeg / 0.9
            else -> valueInDeg
        }
    }
}
