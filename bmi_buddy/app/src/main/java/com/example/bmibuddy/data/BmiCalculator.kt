package com.example.bmibuddy.data

object BmiCalculator {
    
    // PUBLIC_INTERFACE
    fun calculateBmi(weight: Double, height: Double, isMetric: Boolean): Double {
        /**
         * Calculate BMI based on weight and height
         * @param weight in kg (metric) or lbs (imperial)
         * @param height in cm (metric) or inches (imperial)
         * @param isMetric true for metric units, false for imperial
         * @return BMI value
         */
        val weightKg = if (isMetric) weight else weight * 0.453592
        val heightM = if (isMetric) height / 100 else height * 0.0254
        
        return weightKg / (heightM * heightM)
    }
    
    // PUBLIC_INTERFACE
    fun getBmiCategory(bmi: Double): String {
        /**
         * Get BMI category based on WHO guidelines
         * @param bmi BMI value
         * @return Health category string
         */
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 25.0 -> "Normal weight"
            bmi < 30.0 -> "Overweight"
            else -> "Obese"
        }
    }
    
    // PUBLIC_INTERFACE
    fun getBmiTip(category: String): String {
        /**
         * Get health tip based on BMI category
         * @param category BMI category
         * @return Health tip string
         */
        return when (category) {
            "Underweight" -> "Consider consulting a healthcare provider about healthy weight gain strategies."
            "Normal weight" -> "Great! Maintain your healthy lifestyle with balanced diet and regular exercise."
            "Overweight" -> "Consider adopting a healthier diet and increasing physical activity."
            "Obese" -> "Consider consulting a healthcare provider for a comprehensive weight management plan."
            else -> "Consult your healthcare provider for personalized advice."
        }
    }
    
    // PUBLIC_INTERFACE
    fun getCategoryColor(category: String): String {
        /**
         * Get color code for BMI category
         * @param category BMI category
         * @return Color resource name
         */
        return when (category) {
            "Underweight" -> "underweight"
            "Normal weight" -> "normal"
            "Overweight" -> "overweight"
            "Obese" -> "obese"
            else -> "text_primary"
        }
    }
}
