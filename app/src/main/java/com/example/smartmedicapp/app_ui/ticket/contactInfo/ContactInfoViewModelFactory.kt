package com.example.smartmedicapp.app_ui.ticket.contactInfo


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartmedicapp.dataLayer.ServisTicketDatabaseDao

/**
 * This class is useful for creating ContanctInfoModel instances
 */
class ContactInfoViewModelFactory(
    private val detailsKey: Long = 0L ,
    private val dataSource: ServisTicketDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactInfoViewModel::class.java)) {
            return ContactInfoViewModel(detailsKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}