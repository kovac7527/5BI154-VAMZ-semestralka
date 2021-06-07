package com.example.smartmedicapp.app_ui.ticket.contactInfo

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmedicapp.CredentialsManager.CredentialsManager
import com.example.smartmedicapp.dataLayer.ServisTicket
import com.example.smartmedicapp.dataLayer.ServisTicketDatabaseDao
import kotlinx.coroutines.launch
import timber.log.Timber

class  ContactInfoViewModel (
    private val ticketKey: Long = 0L,
    private val dataSource: ServisTicketDatabaseDao
) : ViewModel() {

    val contactName = MutableLiveData<String>(CredentialsManager.getUserProfile().name)
    val contactEmail = MutableLiveData<String>(CredentialsManager.getUserProfile().email)
    val contactPhone = MutableLiveData<String>("")
    val contactAddressPickup = MutableLiveData<String>("")



    var valid  =  MutableLiveData<Boolean>(false)

    val database = dataSource

    var ticketToUpdate: ServisTicket? = null

    private val _navigateToServis = MutableLiveData<ServisTicket>()

    val navigateToServis: LiveData<ServisTicket>
        get() =  _navigateToServis



    fun onSendTicket (){

        viewModelScope.launch {
            ticketToUpdate = database.get(ticketKey)
            Timber.i("yah i got new ticket with key : ${ticketKey}")
            Timber.i("yah i got new ticket with brand : ${ticketToUpdate?.device_brand}")
            _navigateToServis.value = ticketToUpdate
        }



    }

    fun validateForm  () {
        valid.value = (isAddressValid()  &&  isNameValid()
                && isPhoneValid()   && isEmailValid())
    }


    fun doneNavigating() {
        _navigateToServis.value = null
    }

    fun isNameValid(): Boolean {
        if (contactName.value != null) {
            return contactName.value.toString().length > 5
        } else return false
    }

    fun isEmailValid(): Boolean {
        return !TextUtils.isEmpty(contactEmail.value) && android.util.Patterns.EMAIL_ADDRESS.matcher(contactEmail.value).matches();
    }

    fun isPhoneValid(): Boolean {
        val validNumber = "^09(?:[0-9]){8}".toRegex()
        return contactPhone.value != null && contactPhone.value.toString().matches(validNumber)
    }

    fun isAddressValid(): Boolean {
        if (contactAddressPickup.value != null) {
            return contactAddressPickup.value.toString().length > 10
        } else return false
    }

}