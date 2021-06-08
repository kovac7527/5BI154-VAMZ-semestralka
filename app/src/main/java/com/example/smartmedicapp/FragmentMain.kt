package com.example.smartmedicapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.smartmedicapp.CredentialsManager.CredentialsManager
import com.example.smartmedicapp.databinding.FragmentMainBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMain.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMain : Fragment()  {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val navController = findNavController()

        val binding: FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main ,container,false)

        // set a welcome text for currently logged user
        binding.textUserInfo.text = "Vitajte " + CredentialsManager.getUserProfile().name + " ! "

        return binding.root
    }


    override fun onResume() {
        super.onResume()

    }
    override fun onPause() {
        super.onPause()

    }


}