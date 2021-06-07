package com.example.smartmedicapp.app_ui.ticket

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.smartmedicapp.databinding.FragmentCreateTicketBinding
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentCreateTicket.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentCreateTicket : Fragment() {


    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? ,
        savedInstanceState : Bundle?
    ) : View? {

        val binding:  FragmentCreateTicketBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_ticket, container, false)

        val application = requireNotNull(this.activity).application

        val arguments = FragmentCreateTicketArgs.fromBundle(requireArguments())

        val dataSource = ServisDatabase.getInstance(application).servisDatabaseDao
        val viewModelFactory = CreateTicketViewModelFactory(arguments.deviceType, dataSource)

        val createTicketViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(CreateTicketViewModel::class.java)


        binding.createTicketViewModel =  createTicketViewModel
        binding.lifecycleOwner = this

        createTicketViewModel.readOldData()

       val deviceBrand = binding.editTextDeviceBrand
        val deviceModel = binding.editTextDeviceModel
        val deviceProblem = binding.editTextTextDeviceProblem

        deviceBrand.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                createTicketViewModel.validateForm()
                if (!createTicketViewModel.isBrandValid()){
                    deviceBrand.setError("At least 3 characters")
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })



        deviceModel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                createTicketViewModel.validateForm()
                if (!createTicketViewModel.isModelValid()){
                    deviceModel.setError("At least 3 characters")
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        deviceProblem.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                createTicketViewModel.validateForm()
                if (!createTicketViewModel.isProblemValid()){
                    deviceProblem.setError("At least 3 characters")
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })









        createTicketViewModel.navigateToContactInfo.observe(viewLifecycleOwner, Observer { ticket ->
            ticket?.let {
                // We need to get the navController from this, because button is not ready, and it
                // just has to be a view. For some reason, this only matters if we hit stop again
                // after using the back button, not if we hit stop and choose a quality.
                // Also, in the Navigation Editor, for Quality -> Tracker, check "Inclusive" for
                // popping the stack to get the correct behavior if we press stop multiple times
                // followed by back.
                // Also: https://stackoverflow.com/questions/28929637/difference-and-uses-of-oncreate-oncreateview-and-onactivitycreated-in-fra
                this.findNavController().navigate(
                    FragmentCreateTicketDirections.actionCreateTicketFragmentToContantInfoFragment(ticket.detailsId))
                    Timber.i("sending id ${ticket.detailsId} to another fragment")

                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change
                createTicketViewModel.doneNavigating()
            }
        })





        return binding.root
    }

}