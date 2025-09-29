package com.example.unitconverter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
        setupCategoryCards()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
    }

    private fun setupCategoryCards() {
        // Length
        findViewById<CardView>(R.id.cardLength).setOnClickListener {
            openConverter("Length")
        }

        // Area
        findViewById<CardView>(R.id.cardArea).setOnClickListener {
            openConverter("Area")
        }

        // Volume
        findViewById<CardView>(R.id.cardVolume).setOnClickListener {
            openConverter("Volume")
        }

        // Mass
        findViewById<CardView>(R.id.cardMass).setOnClickListener {
            openConverter("Mass")
        }

        // Time
        findViewById<CardView>(R.id.cardTime).setOnClickListener {
            openConverter("Time")
        }

        // Speed
        findViewById<CardView>(R.id.cardSpeed).setOnClickListener {
            openConverter("Speed")
        }

        // Temperature
        findViewById<CardView>(R.id.cardTemperature).setOnClickListener {
            openConverter("Temperature")
        }

        // Density
        findViewById<CardView>(R.id.cardDensity).setOnClickListener {
            openConverter("Density")
        }

        // Energy
        findViewById<CardView>(R.id.cardEnergy).setOnClickListener {
            openConverter("Energy")
        }

        // Angle
        findViewById<CardView>(R.id.cardAngle).setOnClickListener {
            openConverter("Angle")
        }
    }

    private fun openConverter(category: String) {
        val intent = Intent(this, ConverterActivity::class.java)
        intent.putExtra("CATEGORY", category)
        startActivity(intent)
    }
}