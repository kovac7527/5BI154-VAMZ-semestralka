package com.example.smartmedicapp.app_ui.servis

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.smartmedicapp.CredentialsManager.CredentialsManager
import com.example.smartmedicapp.dataLayer.ServisTicketDatabaseDao

class ServisViewModel(
    dataSource: ServisTicketDatabaseDao,
    application: Application
) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via SleepDatabaseDao.
     */
    val database = dataSource

    var tickets = database.getAllTickets(CredentialsManager.getUserProfile().email.toString())



}