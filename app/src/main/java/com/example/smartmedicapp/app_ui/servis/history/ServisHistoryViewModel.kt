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

    //var tickets = database.getAllTicketsByType(CredentialsManager.getUserProfile().email.toString(),1)




    fun readFinishedTickets(){
        viewModelScope.launch {
            _ticketsList.value = database.getAllDoneTickets(CredentialsManager.getUserProfile().email.toString())
        }

    }





}