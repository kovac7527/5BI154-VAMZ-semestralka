package com.example.smartmedicapp.app_ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.smartmedicapp.R
import com.example.smartmedicapp.databinding.FragmentEconomyBinding




/**
 * A simple [Fragment] subclass.
 * Use the [fragment_economy.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragmentEconomy : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentEconomyBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_economy, container, false)

        val application = requireNotNull(this.activity).application

        binding.setLifecycleOwner(this)

        return binding.root
    }
}