package com.example.smartmedicapp.app_ui.ticket.contactInfo

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmedicapp.CredentialsManager.CredentialsManager
import com.example.smartmedicapp.dataLayer.DeviceDetailsTemp
import com.example.smartmedicapp.dataLayer.ServisTicket
import com.example.smartmedicapp.dataLayer.ServisTicketDatabaseDao
import kotlinx.coroutines.launch
/**
 * This class is useful handling and controling user input in contact info form
 *
 */
class  ContactInfoViewModel (
    private val detailsKey: Long = 0L ,
    private val dataSource: ServisTicketDatabaseDao
) : ViewModel() {

    /**
     * Live data of the form
     *
     */
    val contactName = MutableLiveData<String>(CredentialsManager.getUserProfile().name)
    val contactEmail = MutableLiveData<String>(CredentialsManager.getUserProfile().email)
    val contactPhone = MutableLiveData<String>("")
    val contactAddressPickup = MutableLiveData<String>("")

    /**
     * Holding a reference if is form valid
     */
    var valid  =  MutableLiveData<Boolean>(false)
    /**
     * Holding a reference to database
     */
    val database = dataSource

    /**
     * Holding a device details created on previous form
     */
    var detailsToTicket: DeviceDetailsTemp? = null


    /**
     * Object to wich observer listen to ... usefull for navigation
     */
    private val _navigateToServis = MutableLiveData<ServisTicket>()

    val navigateToServis: LiveData<ServisTicket>
        get() =  _navigateToServis


    /**
     * Method for handling sending ticket
     */
    fun onSendTicket (){

        viewModelScope.launch {
            detailsToTicket = database.getTempDeviceDetails(detailsKey)
            val newTicket = ServisTicket(
                CredentialsManager.getUserProfile().email,
                detailsToTicket?.device_brand ,
                detailsToTicket?.device_model,
                detailsToTicket?.device_type!! ,
                detailsToTicket?.problem,
                detailsToTicket?.ticket_note,
                0,
            )

            database.insertServisTicket(newTicket)
            database.clearTemDevDetails()
            _navigateToServis.value = newTicket
        }



    }
    /**
     * This method validate whole user form
     */
    fun validateForm  () {

        valid.value = (isAddressValid()  &&  isNameValid()
                && isPhoneValid()   && isEmailValid())
    }


    fun doneNavigating() {
        _navigateToServis.value = null
    }

    /**
     * This method validate name field in form
     */
    fun isNameValid(): Boolean {
        if (contactName.value != null) {
            return contactName.value.toString().length > 5
        } else return false
    }

    /**
     * This method validate email field in form
     */
    fun isEmailValid(): Boolean {
        return !TextUtils.isEmpty(contactEmail.value) && android.util.Patterns.EMAIL_ADDRESS.matcher(contactEmail.value).matches();
    }

    /**
     * This method validate phone field in form
     */
    fun isPhoneValid(): Boolean {
        val validNumber = "^09(?:[0-9]){8}".toRegex()
        return contactPhone.value != null && contactPhone.value.toString().matches(validNumber)
    }

    /**
     * This method validate address field in form
     */
    fun isAddressValid(): Boolean {
        if (contactAddressPickup.value != null) {
            return contactAddressPickup.value.toString().length > 10
        } else return false
    }

}