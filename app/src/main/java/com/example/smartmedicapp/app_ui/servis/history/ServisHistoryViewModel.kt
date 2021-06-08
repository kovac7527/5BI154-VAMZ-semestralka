package com.example.smartmedicapp.app_ui.servis.history

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmedicapp.CredentialsManager.CredentialsManager
import com.example.smartmedicapp.dataLayer.ServisTicket
import com.example.smartmedicapp.dataLayer.ServisTicketDatabaseDao
import kotlinx.coroutines.launch
/**
 * This Class is usefull for control servis history view
 *
 */
class ServisHistoryViewModel(
    dataSource: ServisTicketDatabaseDao ,
    application: Application
) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via SleepDatabaseDao.
     */
    val database = dataSource

    private val _ticketsList = MutableLiveData<List<ServisTicket>>()

    val tickets : LiveData<List<ServisTicket>>
        get() = _ticketsList





    /**
     * This method set a list of already finished tickets to our created ticket list
     *
     */
    fun readFinishedTickets(){
        viewModelScope.launch {
            _ticketsList.value = database.getAllDoneTickets(CredentialsManager.getUserProfile().email.toString())
        }

    }





}