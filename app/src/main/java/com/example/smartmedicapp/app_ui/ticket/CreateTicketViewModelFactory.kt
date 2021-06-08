package com.example.smartmedicapp.app_ui.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartmedicapp.dataLayer.ServisTicketDatabaseDao
/**
 * This class is useful for creating CreateTicketModel instances
 */
class CreateTicketViewModelFactory(
    private val deviceType: Int = 0 ,
    private val dataSource: ServisTicketDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateTicketViewModel::class.java)) {
            return CreateTicketViewModel( deviceType,dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}