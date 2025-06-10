package com.example.bmibuddy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.example.bmibuddy.data.BmiHistory
import com.example.bmibuddy.data.BmiRecord

class MainActivity : AppCompatActivity() {
    
    private lateinit var unitRadioGroup: RadioGroup
    private lateinit var metricRadio: RadioButton
    private lateinit var imperialRadio: RadioButton
    private lateinit var heightInput: TextInputEditText
    private lateinit var weightInput: TextInputEditText
    private lateinit var heightUnit: TextView
    private lateinit var weightUnit: TextView
    private lateinit var calculateButton: MaterialButton
    private lateinit var resultsCard: MaterialCardView
    private lateinit var bmiValue: TextView
    private lateinit var categoryValue: TextView
    private lateinit var healthTip: TextView
    private lateinit var historyButton: MaterialButton
    private lateinit var settingsButton: MaterialButton
    private lateinit var bmiHistory: BmiHistory
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initViews()
        setupListeners()
    }
    
    private fun initViews() {
        unitRadioGroup = findViewById(R.id.unitRadioGroup)
        metricRadio = findViewById(R.id.metricRadio)
        imperialRadio = findViewById(R.id.imperialRadio)
        heightInput = findViewById(R.id.heightInput)
        weightInput = findViewById(R.id.weightInput)
        heightUnit = findViewById(R.id.heightUnit)
        weightUnit = findViewById(R.id.weightUnit)
        calculateButton = findViewById(R.id.calculateButton)
        resultsCard = findViewById(R.id.resultsCard)
        bmiValue = findViewById(R.id.bmiValue)
        categoryValue = findViewById(R.id.categoryValue)
        healthTip = findViewById(R.id.healthTip)
        historyButton = findViewById(R.id.historyButton)
        settingsButton = findViewById(R.id.settingsButton)
        bmiHistory = BmiHistory(this)
    }
    
    private fun setupListeners() {
        unitRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            updateUnitLabels(checkedId == R.id.metricRadio)
        }
        
        calculateButton.setOnClickListener {
            calculateBmi()
        }
        
        historyButton.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
        
        settingsButton.setOnClickListener {
            Toast.makeText(this, "Settings coming soon!", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun updateUnitLabels(isMetric: Boolean) {
        if (isMetric) {
            heightUnit.text = getString(R.string.cm)
            weightUnit.text = getString(R.string.kg)
        } else {
            heightUnit.text = getString(R.string.in)
            weightUnit.text = getString(R.string.lbs)
        }
        
        heightInput.text?.clear()
        weightInput.text?.clear()
        resultsCard.visibility = View.GONE
    }
    
    private fun calculateBmi() {
        val heightStr = heightInput.text.toString().trim()
        val weightStr = weightInput.text.toString().trim()
        
        if (heightStr.isEmpty() || weightStr.isEmpty()) {
            Toast.makeText(this, getString(R.string.invalid_input), Toast.LENGTH_SHORT).show()
            return
        }
        
        try {
            val height = heightStr.toDouble()
            val weight = weightStr.toDouble()
            val isMetric = metricRadio.isChecked
            
            if (height <= 0 || weight <= 0) {
                Toast.makeText(this, getString(R.string.invalid_input), Toast.LENGTH_SHORT).show()
                return
            }
            
            val bmi = calculateBmiValue(weight, height, isMetric)
            val category = getBmiCategory(bmi)
            val tip = getBmiTip(category)
            
            displayResults(bmi, category, tip)
            
            // Save BMI record to history
            val record = BmiRecord(
                height = height,
                weight = weight,
                bmi = bmi,
                category = category,
                isMetric = isMetric
            )
            bmiHistory.addRecord(record)
            
        } catch (e: NumberFormatException) {
            Toast.makeText(this, getString(R.string.invalid_input), Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun calculateBmiValue(weight: Double, height: Double, isMetric: Boolean): Double {
        val weightKg = if (isMetric) weight else weight * 0.453592
        val heightM = if (isMetric) height / 100 else height * 0.0254
        return weightKg / (heightM * heightM)
    }
    
    private fun getBmiCategory(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 25.0 -> "Normal weight"
            bmi < 30.0 -> "Overweight"
            else -> "Obese"
        }
    }
    
    private fun getBmiTip(category: String): String {
        return when (category) {
            "Underweight" -> "Consider consulting a healthcare provider about healthy weight gain strategies."
            "Normal weight" -> "Great! Maintain your healthy lifestyle with balanced diet and regular exercise."
            "Overweight" -> "Consider adopting a healthier diet and increasing physical activity."
            "Obese" -> "Consider consulting a healthcare provider for a comprehensive weight management plan."
            else -> "Consult your healthcare provider for personalized advice."
        }
    }
    
    private fun displayResults(bmi: Double, category: String, tip: String) {
        bmiValue.text = String.format("%.1f", bmi)
        categoryValue.text = category
        healthTip.text = tip
        
        val colorResId = when (category) {
            "Underweight" -> R.color.underweight
            "Normal weight" -> R.color.normal
            "Overweight" -> R.color.overweight
            "Obese" -> R.color.obese
            else -> R.color.text_primary
        }
        categoryValue.setTextColor(ContextCompat.getColor(this, colorResId))
        
        resultsCard.visibility = View.VISIBLE
    }
}
