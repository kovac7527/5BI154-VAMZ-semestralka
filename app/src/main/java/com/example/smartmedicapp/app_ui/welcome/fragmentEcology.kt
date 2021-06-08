package com.example.smartmedicapp.app_ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.smartmedicapp.R
import com.example.smartmedicapp.databinding.FragmentEcologyBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_ecology.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragmentEcology : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentEcologyBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_ecology, container, false)

        val application = requireNotNull(this.activity).application

        binding.setLifecycleOwner(this)

        return binding.root
    }
}