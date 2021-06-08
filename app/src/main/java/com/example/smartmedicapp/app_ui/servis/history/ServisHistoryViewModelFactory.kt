package com.example.smartmedicapp.app_ui.servis.history

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartmedicapp.dataLayer.ServisTicketDatabaseDao

/**
 * This class is useful for creating ServisHistoryModel instances
 *
 */
class ServisHistoryViewModelFactory (
    private val dataSource: ServisTicketDatabaseDao ,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServisHistoryViewModel::class.java)) {
            return ServisHistoryViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}