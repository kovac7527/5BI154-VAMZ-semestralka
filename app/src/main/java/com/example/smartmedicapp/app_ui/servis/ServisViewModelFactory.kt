package com.example.smartmedicapp.app_ui.servis

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartmedicapp.dataLayer.ServisTicketDatabaseDao

class ServisViewModelFactory (
    private val dataSource: ServisTicketDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServisViewModel::class.java)) {
            return ServisViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}