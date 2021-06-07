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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentServisBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_servis, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = ServisDatabase.getInstance(application).servisDatabaseDao
        val viewModelFactory = ServisViewModelFactory(dataSource, application)

        val servisViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(ServisViewModel::class.java)

        binding.servisViewModel =  servisViewModel
        binding.lifecycleOwner = this

        servisViewModel.buttonConsole = binding.buttonFilterConsole
        servisViewModel.buttonPc = binding.buttonFilterPc
        servisViewModel.buttonPhone = binding.buttonFilterMobile





        val adapter = ServisTicketAdapter()
        binding.ticketList.adapter = adapter
        servisViewModel.adapter = adapter

        binding.floatingActionButton2.setOnClickListener {
         // FragmentServisDirections.actionFragmentServisToCreateTicketFragment(servisViewModel.deviceType)
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








        return binding.root
    }


}