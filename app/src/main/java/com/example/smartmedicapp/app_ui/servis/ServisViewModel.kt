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
/**
 * This class is useful for handling user actions on servis view
 *
 */
class ServisViewModel(
    dataSource: ServisTicketDatabaseDao,
    application: Application
) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via SleepDatabaseDao.
     */
    val database = dataSource
    /**
     * Hold a reference to actual selected device type
     *
     */
    var deviceType = 2

    // reference to buttons for handling filters of list
    var buttonPhone : Button? = null
    var buttonConsole : Button? = null
    var buttonPc : Button? = null

    /**
     * list of actually displayed tickets
     *
     */
    private val _ticketsList = MutableLiveData<List<ServisTicket>>()

    val tickets : LiveData<List<ServisTicket>>
        get() = _ticketsList
    /**
     * Method to handle click on mobilephone filter
     *
     */
    fun onClickPhoneFilter(){
        buttonPhone?.isSelected = true;
        buttonConsole?.isSelected = false;
        buttonPc?.isSelected = false;
        deviceType = 1
        viewModelScope.launch {
            _ticketsList.value = database.getAllTicketsByType(CredentialsManager.getUserProfile().email.toString(),deviceType)
        }



    }
    /**
     * Method to handle click on pc filter
     *
     */
    fun onClickPcFilter(){
        buttonPc?.isSelected = true;
        buttonConsole?.isSelected = false;
        buttonPhone?.isSelected = false;
        deviceType = 2
        viewModelScope.launch {
        _ticketsList.value = database.getAllTicketsByType(CredentialsManager.getUserProfile().email.toString(),deviceType)
        }
    }
    /**
     * Method to handle click on console filter
     *
     */
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