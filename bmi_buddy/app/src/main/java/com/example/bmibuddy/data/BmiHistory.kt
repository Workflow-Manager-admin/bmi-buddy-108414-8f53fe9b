package com.example.bmibuddy.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BmiHistory(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("bmi_history", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val key = "bmi_records"
    
    // PUBLIC_INTERFACE
    fun addRecord(record: BmiRecord) {
        /**
         * Add a new BMI record to history
         * @param record BMI record to add
         */
        val records = getRecords().toMutableList()
        records.add(0, record) // Add to beginning for chronological order
        
        // Keep only last 50 records to avoid storage issues
        if (records.size > 50) {
            records.removeAt(records.size - 1)
        }
        
        saveRecords(records)
    }
    
    // PUBLIC_INTERFACE
    fun getRecords(): List<BmiRecord> {
        /**
         * Get all BMI records from history
         * @return List of BMI records
         */
        val json = prefs.getString(key, null) ?: return emptyList()
        val type = object : TypeToken<List<BmiRecord>>() {}.type
        return try {
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    // PUBLIC_INTERFACE
    fun clearHistory() {
        /**
         * Clear all BMI history records
         */
        prefs.edit().remove(key).apply()
    }
    
    private fun saveRecords(records: List<BmiRecord>) {
        val json = gson.toJson(records)
        prefs.edit().putString(key, json).apply()
    }
}
