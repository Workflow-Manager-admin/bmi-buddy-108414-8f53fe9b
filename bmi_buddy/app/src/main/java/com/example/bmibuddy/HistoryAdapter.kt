package com.example.bmibuddy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bmibuddy.data.BmiRecord

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    
    private var records = listOf<BmiRecord>()
    
    // PUBLIC_INTERFACE
    fun updateRecords(newRecords: List<BmiRecord>) {
        /**
         * Update the list of BMI records and refresh the view
         * @param newRecords List of BMI records to display
         */
        records = newRecords
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(records[position])
    }
    
    override fun getItemCount() = records.size
    
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bmiText: TextView = itemView.findViewById(R.id.bmiText)
        private val categoryText: TextView = itemView.findViewById(R.id.categoryText)
        private val measurementsText: TextView = itemView.findViewById(R.id.measurementsText)
        private val dateText: TextView = itemView.findViewById(R.id.dateText)
        
        fun bind(record: BmiRecord) {
            bmiText.text = String.format("%.1f", record.bmi)
            categoryText.text = record.category
            measurementsText.text = "${record.getFormattedHeight()} • ${record.getFormattedWeight()}"
            dateText.text = record.getFormattedDate()
            
            // Set category color
            val colorResId = when (record.category) {
                "Underweight" -> R.color.underweight
                "Normal weight" -> R.color.normal
                "Overweight" -> R.color.overweight
                "Obese" -> R.color.obese
                else -> R.color.text_primary
            }
            categoryText.setTextColor(ContextCompat.getColor(itemView.context, colorResId))
        }
    }
}
