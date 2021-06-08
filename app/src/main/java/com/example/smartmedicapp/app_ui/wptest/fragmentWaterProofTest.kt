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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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

        binding.buttonContinueToTest.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentWaterProofTest_to_WPTestingFragment)
        }

        return binding.root
    }


}