package com.example.smartmedicapp.app_ui.wptest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.smartmedicapp.R
import com.example.smartmedicapp.databinding.FragmentWaterProofTestBinding


/**
 * A simple [Fragment] subclass.
 * Use the [fragmentWaterProofTest.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragmentWaterProofTest : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding: FragmentWaterProofTestBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_water_proof_test, container, false)
        // setting the listener to button for navigation to real test
        binding.buttonContinueToTest.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentWaterProofTest_to_WPTestingFragment)
        }

        return binding.root
    }


}