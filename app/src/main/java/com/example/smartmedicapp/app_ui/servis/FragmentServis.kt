package com.example.smartmedicapp.app_ui.servis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.smartmedicapp.R
import com.example.smartmedicapp.dataLayer.ServisDatabase
import com.example.smartmedicapp.databinding.FragmentServisBinding
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber



/**
 * A simple [Fragment] subclass.
 * Use the [FragmentServis.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentServis : Fragment() {

    /**
     * This method ovverride onCreate
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    /**
     * This method ovverride onCreateView
     * Here we inflate layout and initialize databinding, and create adapter for our list
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val binding: FragmentServisBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_servis, container, false)

        val application = requireNotNull(this.activity).application
        // get instance of ou database
        val dataSource = ServisDatabase.getInstance(application).servisDatabaseDao
        val viewModelFactory = ServisViewModelFactory(dataSource, application)

        val servisViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(ServisViewModel::class.java)

        binding.servisViewModel =  servisViewModel
        binding.lifecycleOwner = this

        // providing buttons to model for control
        servisViewModel.buttonConsole = binding.buttonFilterConsole
        servisViewModel.buttonPc = binding.buttonFilterPc
        servisViewModel.buttonPhone = binding.buttonFilterMobile

        //creating an adapter

        val adapter = ServisTicketAdapter()
        binding.ticketList.adapter = adapter


        // add an action to floating button to redirect to another fragment
        binding.floatingActionButton2.setOnClickListener {

            this.findNavController().navigate(
                FragmentServisDirections.actionFragmentServisToCreateTicketFragment(servisViewModel.deviceType))

        Timber.i("pressed floating")
        }


        servisViewModel.tickets.observe(viewLifecycleOwner, Observer {
            it?.let {
                Timber.i("data changed")
                adapter.data = it
            }

        })

        // setting a default behavior for our list
        servisViewModel.onClickPhoneFilter()
        return binding.root
    }


}