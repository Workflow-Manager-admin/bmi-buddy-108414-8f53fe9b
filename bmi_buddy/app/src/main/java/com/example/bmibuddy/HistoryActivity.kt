package com.example.bmibuddy

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bmibuddy.data.BmiHistory
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class HistoryActivity : AppCompatActivity() {
    
    private lateinit var toolbar: MaterialToolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyState: View
    private lateinit var clearHistoryButton: MaterialButton
    private lateinit var bmiHistory: BmiHistory
    private lateinit var adapter: HistoryAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        
        initViews()
        setupToolbar()
        setupRecyclerView()
        loadHistory()
    }
    
    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.historyRecyclerView)
        emptyState = findViewById(R.id.emptyState)
        clearHistoryButton = findViewById(R.id.clearHistoryButton)
        bmiHistory = BmiHistory(this)
    }
    
    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun setupRecyclerView() {
        adapter = HistoryAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        
        clearHistoryButton.setOnClickListener {
            bmiHistory.clearHistory()
            loadHistory()
            Toast.makeText(this, getString(R.string.history_cleared), Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun loadHistory() {
        val records = bmiHistory.getRecords()
        
        if (records.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyState.visibility = View.VISIBLE
            clearHistoryButton.isEnabled = false
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyState.visibility = View.GONE
            clearHistoryButton.isEnabled = true
            adapter.updateRecords(records)
        }
    }
}
