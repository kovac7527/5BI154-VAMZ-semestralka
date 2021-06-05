package com.example.smartmedicapp

import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.smartmedicapp.databinding.FragmentMainBinding
import timber.log.Timber


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMain.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMain : Fragment(), SensorEventListener {

    private lateinit var mSensorManager : SensorManager
    private var mBarometer : Sensor ?= null
    private var resume = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mSensorManager = this.requireActivity().getSystemService(Activity.SENSOR_SERVICE) as SensorManager
        mBarometer = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        mSensorManager.registerListener(this, this.mBarometer, SensorManager.SENSOR_DELAY_NORMAL);


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val navController = findNavController()

        val binding: FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main ,container,false)



        return binding.root
    }

    override fun onSensorChanged(event: SensorEvent?) {
        Timber.i("something changed")
        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_PRESSURE) {
                Timber.i("im here presuure is : ${event.values[0].toString()}")
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }


}