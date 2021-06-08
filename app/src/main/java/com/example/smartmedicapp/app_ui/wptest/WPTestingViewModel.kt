package com.example.smartmedicapp.app_ui.wptest

import android.app.Application
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartmedicapp.R
import com.example.smartmedicapp.SmartMedicApplication
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
    var resultText : TextView? = null

    var pressure : Float = 0F

    var maxPressure : Float = 0F
    var minPressure : Float = 10000F

    var startTestTime = 0L
    var endTestTime = 0L
    var timeTest = 0L


    var isPressed : Boolean = false

    val message = MutableLiveData<String>(SmartMedicApplication.resourses.getString(R.string.WPTesting_first_message))




    fun onClickButton(){

        Timber.i("clicked")
        image?.setImageResource(R.mipmap.circle_pushed_green_svg)
        startTestTime = System.currentTimeMillis()


        message.value =SmartMedicApplication.resourses.getString(R.string.WPTesting_keep_pushing)
        isPressed = true


    }

    fun resetTest() {
        maxPressure = 0F
        minPressure  = 10000F
    }

    fun onReleaseButton(){
        Timber.i("released")
        image?.setImageResource(R.mipmap.circle_push_blue_svg)

        if  (timeTest < 1500 ) {
            resultText?.setTextColor(Color.parseColor("#fcb70a"))
            message.value = SmartMedicApplication.resourses.getString(R.string.WPTesting_test_fail)
        } else {
            if ((maxPressure - minPressure) > 0.9) {
                resultText?.setTextColor(Color.parseColor("#22e646"))
                message.value = SmartMedicApplication.resourses.getString(R.string.WPTesting_test_pass) + "${maxPressure - minPressure}"
            } else {
                resultText?.setTextColor(Color.parseColor("#fc0a37"))
                message.value = SmartMedicApplication.resourses.getString(R.string.WPTesting_test_nopass) + "${maxPressure - minPressure}"
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
            if  (timeTest > 1500 ) {
                resultText?.setTextColor(Color.parseColor("#0a5bfc"))
                message.value = SmartMedicApplication.resourses.getString(R.string.WPTesting_release)

            }
        } else {
            if (pressure < minPressure) {
                minPressure = pressure
                Timber.i("changed min" +pressure.toString())
            }
        }


    }






}