package com.example.smartmedicapp.app_ui.ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmedicapp.dataLayer.DeviceDetailsTemp
import com.example.smartmedicapp.dataLayer.ServisTicketDatabaseDao
import kotlinx.coroutines.launch

/**
 * This class is useful handling and controling user input in creating ticket
 *
 */

class CreateTicketViewModel (
    private val deviceType: Int = 0,
    private val  dataSource: ServisTicketDatabaseDao
) : ViewModel() {

    val database = dataSource
    // variable to hold last unsentTicket
    var lastDevDetails : DeviceDetailsTemp? = null

    val deviceBrand = MutableLiveData<String>("")
    val deviceModel = MutableLiveData<String>("")
    val deviceProblem = MutableLiveData<String>("")
    val ticketNote = MutableLiveData<String>("")

    var valid  =  MutableLiveData<Boolean>(false)


    // variable for observer, useful for navigation to contact info fragment
    private val _navigateToContactInfo = MutableLiveData<DeviceDetailsTemp>()

    val navigateToContactInfo: LiveData<DeviceDetailsTemp>
        get() =  _navigateToContactInfo


    /**
     * Function which handle user action to continue to next fragment
       */
    fun onCreateNewTicket (){
    //launch couroutine
        viewModelScope.launch {
                // create new instance and save to db
                val newDetails = DeviceDetailsTemp(
                    deviceBrand.value,
                    deviceModel.value,
                    deviceType,
                    deviceProblem.value,
                    ticketNote.value
                )
            database.insertDeviceDetails(newDetails)
            _navigateToContactInfo.value = database.getLastDevDetails()

        }



    }
    /**
     * Function which validates whole form for creating a ticket
     */
    fun validateForm  () {
        valid.value = (isBrandValid()  &&  isModelValid()
                && isProblemValid())
    }

    /**
     * Called when navigation is done
     */
    fun doneNavigating() {
        _navigateToContactInfo.value = null
    }

    /**
     * Function which validates brand input
     */

    fun isBrandValid(): Boolean {
        if (deviceBrand.value != null) {
            return deviceBrand.value.toString().length > 3
        } else return false
    }

    /**
     * Function which validates model input
     */
    fun isModelValid(): Boolean {
        if (deviceModel.value != null) {
            return deviceModel.value.toString().length > 3
        } else return false
    }

    /**
     * Function which validates Probleminput
     */
    fun isProblemValid(): Boolean {
        if (deviceProblem.value != null) {
            return deviceProblem.value.toString().length > 3
        } else return false
    }

    /**
     * Function which reads old data from ticket if there are any then fills the form
     */

    fun readOldData() {
        viewModelScope.launch {
            lastDevDetails = database.getLastDevDetails()
            if (lastDevDetails != null) {

                deviceBrand.value = lastDevDetails?.device_brand
                deviceModel.value = lastDevDetails?.device_model
                deviceProblem.value = lastDevDetails?.problem
                ticketNote.value = lastDevDetails?.ticket_note
            }

        }

    }

}