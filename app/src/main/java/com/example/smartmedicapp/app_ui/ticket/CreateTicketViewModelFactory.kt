package com.example.smartmedicapp.app_ui.ticket

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartmedicapp.dataLayer.ServisTicketDatabaseDao

class CreateTicketViewModelFactory(
    private val dataSource: ServisTicketDatabaseDao ,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateTicketViewModel::class.java)) {
            return CreateTicketViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}