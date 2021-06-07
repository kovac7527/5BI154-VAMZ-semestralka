package com.example.smartmedicapp.app_ui.ticket

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmedicapp.dataLayer.DeviceDetailsTemp
import com.example.smartmedicapp.dataLayer.ServisTicketDatabaseDao
import kotlinx.coroutines.launch

class CreateTicketViewModel (
    dataSource: ServisTicketDatabaseDao ,
    application: Application
) : ViewModel() {

    val database = dataSource

    var lastDevDetails : DeviceDetailsTemp? = null

    val deviceBrand = MutableLiveData<String>("")
    val deviceModel = MutableLiveData<String>("")
    val deviceProblem = MutableLiveData<String>("")
    val ticketNote = MutableLiveData<String>("")

    var valid  =  MutableLiveData<Boolean>(false)



    private val _navigateToContactInfo = MutableLiveData<DeviceDetailsTemp>()

    val navigateToContactInfo: LiveData<DeviceDetailsTemp>
        get() =  _navigateToContactInfo



    fun onCreateNewTicket (){

        viewModelScope.launch {

                val newDetails = DeviceDetailsTemp(
                    deviceBrand.value,
                    deviceModel.value,
                    1,
                    deviceProblem.value,
                    ticketNote.value
                )
            database.insertDeviceDetails(newDetails)
            _navigateToContactInfo.value = database.getLastDevDetails()

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

    fun readOldData() {
        viewModelScope.launch {
            lastDevDetails = database.getLastDevDetails()
             deviceBrand.value = lastDevDetails?.device_brand
            deviceModel.value = lastDevDetails?.device_model
            deviceProblem.value = lastDevDetails?.problem
            ticketNote.value = lastDevDetails?.ticket_note

        }

    }

}