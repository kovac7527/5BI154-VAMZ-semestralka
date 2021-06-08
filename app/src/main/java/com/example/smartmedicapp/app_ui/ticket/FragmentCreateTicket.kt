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

    /**
     * Function called when view is created
     */

    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? ,
        savedInstanceState : Bundle?
    ) : View? {
        //initialize binding
        val binding:  FragmentCreateTicketBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_ticket, container, false)

        val application = requireNotNull(this.activity).application

        // get argument from bundle
        val arguments = FragmentCreateTicketArgs.fromBundle(requireArguments())

        // get database instance
        val dataSource = ServisDatabase.getInstance(application).servisDatabaseDao
        val viewModelFactory = CreateTicketViewModelFactory(arguments.deviceType, dataSource)

        val createTicketViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(CreateTicketViewModel::class.java)


        binding.createTicketViewModel =  createTicketViewModel
        binding.lifecycleOwner = this

        // force model to try read old user data
        createTicketViewModel.readOldData()

        // getting inputs field for future validation
       val deviceBrand = binding.editTextDeviceBrand
        val deviceModel = binding.editTextDeviceModel
        val deviceProblem = binding.editTextTextDeviceProblem


        // add a listener to input when its changed
        deviceBrand.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                //validateForm whole form
                createTicketViewModel.validateForm()
                if (!createTicketViewModel.isBrandValid()){

                    // if validation is bad throw error for field
                    deviceBrand.setError(getString(R.string.input_error_short_text))
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })


        // add a listener to input when its changed
        deviceModel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                //validateForm whole form
                createTicketViewModel.validateForm()
                if (!createTicketViewModel.isModelValid()){

                    // if validation is bad throw error for field
                    deviceModel.setError(getString(R.string.input_error_short_text))
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        // add a listener to input when its changed
        deviceProblem.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                //validateForm whole form
                createTicketViewModel.validateForm()
                if (!createTicketViewModel.isProblemValid()){

                    // if validation is bad throw error for field
                    deviceProblem.setError(getString(R.string.input_error_short_text))
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        // add observer to variable of model, when state is changed other than null then navigate
        createTicketViewModel.navigateToContactInfo.observe(viewLifecycleOwner, Observer { ticket ->
            ticket?.let {

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