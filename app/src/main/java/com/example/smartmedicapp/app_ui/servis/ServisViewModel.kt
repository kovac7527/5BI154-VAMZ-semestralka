package com.example.smartmedicapp.app_ui.servis

import android.app.Application
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmedicapp.CredentialsManager.CredentialsManager
import com.example.smartmedicapp.dataLayer.ServisTicket
import com.example.smartmedicapp.dataLayer.ServisTicketDatabaseDao
import kotlinx.coroutines.launch

class ServisViewModel(
    dataSource: ServisTicketDatabaseDao,
    application: Application
) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via SleepDatabaseDao.
     */
    val database = dataSource

    var deviceType = 2

    var adapter : ServisTicketAdapter? = null

    var buttonPhone : Button? = null
    var buttonConsole : Button? = null
    var buttonPc : Button? = null


    private val _ticketsList = MutableLiveData<List<ServisTicket>>()

    val tickets : LiveData<List<ServisTicket>>
        get() = _ticketsList

    //var tickets = database.getAllTicketsByType(CredentialsManager.getUserProfile().email.toString(),1)




    fun onClickPhoneFilter(){
        buttonPhone?.isSelected = true;
        buttonConsole?.isSelected = false;
        buttonPc?.isSelected = false;
        deviceType = 1
        viewModelScope.launch {
            _ticketsList.value = database.getAllTicketsByType(CredentialsManager.getUserProfile().email.toString(),deviceType)
        }



    }

    fun onClickPcFilter(){
        buttonPc?.isSelected = true;
        buttonConsole?.isSelected = false;
        buttonPhone?.isSelected = false;
        deviceType = 2
        viewModelScope.launch {
        _ticketsList.value = database.getAllTicketsByType(CredentialsManager.getUserProfile().email.toString(),deviceType)
        }
    }

    fun onClickConsoleFilter(){
        buttonConsole?.isSelected = true;
        buttonPhone?.isSelected = false;
        buttonPc?.isSelected = false;
        deviceType = 3
        viewModelScope.launch {
        _ticketsList.value = database.getAllTicketsByType(CredentialsManager.getUserProfile().email.toString(),deviceType)
        }
    }




}