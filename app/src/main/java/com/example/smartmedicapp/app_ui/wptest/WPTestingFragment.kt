package com.example.smartmedicapp.app_ui.wptest

import android.annotation.SuppressLint
import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.smartmedicapp.R
import com.example.smartmedicapp.dataLayer.ServisDatabase
import com.example.smartmedicapp.databinding.FragmentWPTestingBinding


/**
 * A simple [Fragment] subclass.
 * Use the [WPTestingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WPTestingFragment : Fragment() , SensorEventListener {

    private lateinit var mSensorManager : SensorManager
    private var mBarometer : Sensor ?= null
    private lateinit var viewModel : WPTestingViewModel

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        mSensorManager = this.requireActivity().getSystemService(Activity.SENSOR_SERVICE) as SensorManager
        mBarometer = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        mSensorManager.registerListener(this, this.mBarometer, SensorManager.SENSOR_DELAY_NORMAL);


    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentWPTestingBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_w_p_testing, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = ServisDatabase.getInstance(application).servisDatabaseDao
        val viewModelFactory = WPTestingViewModelFactory(dataSource, application)

        val wpTestingViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(WPTestingViewModel::class.java)

        binding.testViewModel = wpTestingViewModel
        binding.lifecycleOwner = this

        viewModel = wpTestingViewModel

        val image =  binding.imageButtonTest





       image.setOnTouchListener(OnTouchListener { v , event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                wpTestingViewModel.onClickButton()
            } else if (event.action == MotionEvent.ACTION_UP) {
                wpTestingViewModel.onReleaseButton()
            }
            true
        })


        wpTestingViewModel.image =  image
        wpTestingViewModel.resultText = binding.textView2









        return binding.root
    }

    override fun onSensorChanged(event: SensorEvent?) {

        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_PRESSURE) {
                viewModel.onChangePressure(event.values[0])
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor? , accuracy: Int) {

    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this, mBarometer, SensorManager.SENSOR_DELAY_NORMAL)
    }
    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
    }



}