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

/**
 * This class is useful for handilng whole testing of a water proof test of the device
 */
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

    /**
     * Holds actual pressure
     */
    var pressure : Float = 0F

    /**
     * Holds maximum pressure registered yet
     */
    var maxPressure : Float = 0F

    /**
     * Holds minimum pressure registered yet
     */
    var minPressure : Float = 10000F


    /**
     * Holds start time of the test
     */
    var startTestTime = 0L

    /**
     * Holds time duration of the test
     */
    var timeTest = 0L


    /**
     * Holds value if the button is pressed
     */
    var isPressed : Boolean = false


    /**
     * Message to interact with user
     */
    val message = MutableLiveData<String>(SmartMedicApplication.resourses.getString(R.string.WPTesting_first_message))



    /**
     * Method which is implemented for hadle actions when button is pressed
     */
    fun onClickButton(){

        Timber.i("clicked")
        image?.setImageResource(R.mipmap.circle_pushed_green_svg)
        startTestTime = System.currentTimeMillis()


        message.value =SmartMedicApplication.resourses.getString(R.string.WPTesting_keep_pushing)
        isPressed = true


    }

     /**
     * Function to reset test
     */
    fun resetTest() {
        maxPressure = 0F
        minPressure  = 10000F
    }



    /**
     * Function handle button release
     */
    fun onReleaseButton(){
        Timber.i("released")
        // set button image back to normal
        image?.setImageResource(R.mipmap.circle_push_blue_svg)


        //check if button was pressed long enough
        if  (timeTest < 1500 ) {
            // test failed try again
            resultText?.setTextColor(Color.parseColor("#fcb70a"))
            message.value = SmartMedicApplication.resourses.getString(R.string.WPTesting_test_fail)
        } else {
            // testing if device should be waterproof
            if ((maxPressure - minPressure) > 0.9) {
                // differnce in pressure is big device is waterproof
                resultText?.setTextColor(Color.parseColor("#22e646"))
                message.value = SmartMedicApplication.resourses.getString(R.string.WPTesting_test_pass) + "${maxPressure - minPressure}"
            } else {
                // differnce in pressure is small device is not waterproof
                resultText?.setTextColor(Color.parseColor("#fc0a37"))
                message.value = SmartMedicApplication.resourses.getString(R.string.WPTesting_test_nopass) + "${maxPressure - minPressure}"
            }
        }

        // set state back to buttun is not pressed
        isPressed = false

        // rest values for nech test
        resetTest()
    }


    /**
     * Function which handles pressure changes
     */
    fun onChangePressure(pressure : Float){
        //Timber.i(pressure.toString())
        // handle siuation when pressure changes
        if (isPressed) {
            // if pressed check if pressure  is raising and note max value
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
            // if not pressed heck if pressure  is falling  and note min value
                // also registering deafult value of enviroment
            if (pressure < minPressure) {
                // register min pressure if we got small pressure
                minPressure = pressure
                Timber.i("changed min" +pressure.toString())
            }
        }


    }



}