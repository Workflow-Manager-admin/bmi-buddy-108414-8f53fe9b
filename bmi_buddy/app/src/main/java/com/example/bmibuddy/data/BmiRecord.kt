package com.example.bmibuddy.data

import java.text.SimpleDateFormat
import java.util.*

data class BmiRecord(
    val height: Double,
    val weight: Double,
    val bmi: Double,
    val category: String,
    val isMetric: Boolean,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun getFormattedDate(): String {
        val formatter = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        return formatter.format(Date(timestamp))
    }
    
    fun getFormattedHeight(): String {
        return if (isMetric) {
            "${height.toInt()} cm"
        } else {
            val feet = (height / 12).toInt()
            val inches = (height % 12).toInt()
            "$feet'$inches\""
        }
    }
    
    fun getFormattedWeight(): String {
        return if (isMetric) {
            "${weight.toInt()} kg"
        } else {
            "${weight.toInt()} lbs"
        }
    }
}
