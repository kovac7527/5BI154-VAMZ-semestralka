package com.example.smartmedicapp.app_ui.ticket

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmedicapp.CredentialsManager.CredentialsManager
import com.example.smartmedicapp.dataLayer.ServisTicket
import com.example.smartmedicapp.dataLayer.ServisTicketDatabaseDao
import kotlinx.coroutines.launch

class CreateTicketViewModel (
    dataSource: ServisTicketDatabaseDao ,
    application: Application
) : ViewModel() {



    val deviceBrand = MutableLiveData<String>("")
    val deviceModel = MutableLiveData<String>("")
    val deviceProblem = MutableLiveData<String>("")
    val ticketNote = MutableLiveData<String>("")

    var valid  =  MutableLiveData<Boolean>(false)

    val database = dataSource

    private val _navigateToContactInfo = MutableLiveData<ServisTicket>()

    val navigateToContactInfo: LiveData<ServisTicket>
        get() =  _navigateToContactInfo



    fun onCreateNewTicket (){

        viewModelScope.launch {

                val newTicket = ServisTicket(
                    CredentialsManager.getUserProfile().email ,
                    deviceBrand.value ,
                    deviceModel.value ,
                    1 ,
                    deviceProblem.value ,
                    ticketNote.value ,
                    0

                )
            database.insert(newTicket)
            _navigateToContactInfo.value = database.getLastTicket()

        }



    }
    fun validateForm  () {
        valid.value = (isBrandValid()  &&  isModelValid()
                && isProblemValid())
    }


    fun doneNavigating() {
        _navigateToContactInfo.value = null
    }

    fun isBrandValid(): Boolean {
        if (deviceBrand.value != null) {
            return deviceBrand.value.toString().length > 3
        } else return false
    }

    fun isModelValid(): Boolean {
        if (deviceModel.value != null) {
            return deviceModel.value.toString().length > 3
        } else return false
    }

    fun isProblemValid(): Boolean {
        if (deviceProblem.value != null) {
            return deviceProblem.value.toString().length > 3
        } else return false
    }

}