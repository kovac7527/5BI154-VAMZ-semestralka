package com.example.smartmedicapp.app_ui.servis.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.smartmedicapp.R
import com.example.smartmedicapp.app_ui.servis.ServisTicketAdapter
import com.example.smartmedicapp.dataLayer.ServisDatabase
import com.example.smartmedicapp.databinding.FragmentServisHistoryBinding
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 * Use the [fragmentServisHistory.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragmentServisHistory : Fragment() {
    /**
     * This method ovverride onCreate
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    /**
     * This method ovverride onCreateView
     * Here we apply databinding and connecting adapter of recyclerview to the model
     * Here we also use observer too observe list dataset
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentServisHistoryBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_servis_history, container, false)

        val application = requireNotNull(this.activity).application
        // Getting the instance of database
        val dataSource = ServisDatabase.getInstance(application).servisDatabaseDao
        val viewModelFactory = ServisHistoryViewModelFactory(dataSource, application)

        val servisHistoryViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(ServisHistoryViewModel::class.java)

        // Binding model to the view
        binding.servisHistoryViewModel =  servisHistoryViewModel
        servisHistoryViewModel.readFinishedTickets()
        binding.lifecycleOwner = this

        // Creating new adapter for view
        val adapter = ServisTicketAdapter()
        binding.ticketList.adapter = adapter


        //adding observer to tickets list
        servisHistoryViewModel.tickets.observe(viewLifecycleOwner, Observer {
            it?.let {
                Timber.i("data changed")
                adapter.data = it
            }

        })


        return binding.root
    }


}