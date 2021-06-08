package com.example.smartmedicapp.app_ui.wptest

import android.app.Application
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartmedicapp.CredentialsManager.CredentialsManager
import com.example.smartmedicapp.R
import com.example.smartmedicapp.dataLayer.ServisTicketDatabaseDao
import timber.log.Timber

class WPTestingViewModel(
    dataSource: ServisTicketDatabaseDao,
    application: Application
) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via SleepDatabaseDao.
     */
    val database = dataSource

    var image : ImageView? = null

    var pressure : Float = 0F

    var maxPressure : Float = 0F
    var minPressure : Float = 10000F

    var startTestTime = 0L
    var endTestTime = 0L
    var timeTest = 0L


    var isPressed : Boolean = false

    val message = MutableLiveData<String>(CredentialsManager.getUserProfile().name)




    fun onClickButton(){

        Timber.i("clicked")
        image?.setImageResource(R.mipmap.circle_pushed_green_svg)
        startTestTime = System.currentTimeMillis()


        message.value = "Tlačte na tlačidlo"
        isPressed = true


    }

    fun resetTest() {
        maxPressure = 0F
        minPressure  = 10000F
    }

    fun onReleaseButton(){
        Timber.i("released")
        image?.setImageResource(R.mipmap.circle_push_blue_svg)

        if  (timeTest < 3000 ) {
            message.value = "Test neúspešný opakujte znova"
        } else {
            if ((maxPressure - minPressure) > 0.9) {
                message.value = "Vaše zariadenie je vodeodolné  ${maxPressure - minPressure}"
            } else {
                message.value = "Vaše zariadenie nieje vodeodolné  ${maxPressure - minPressure}"
            }
        }
        isPressed = false
        resetTest()
    }

    fun onChangePressure(pressure : Float){
        //Timber.i(pressure.toString())
        if (isPressed) {
            if (pressure > maxPressure) {
                maxPressure = pressure
                Timber.i("changed max" +pressure.toString())
            }
            var actualTime = System.currentTimeMillis()
            timeTest = actualTime - startTestTime
            if  (timeTest > 3000 ) {
                message.value = "Uvoľnite tlačidlo"
            }
        } else {
            if (pressure < minPressure) {
                minPressure = pressure
                Timber.i("changed min" +pressure.toString())
            }
        }


    }






}