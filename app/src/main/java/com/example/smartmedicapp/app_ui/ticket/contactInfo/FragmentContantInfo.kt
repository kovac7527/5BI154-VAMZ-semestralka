package com.example.smartmedicapp.app_ui.ticket.contactInfo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.smartmedicapp.R
import com.example.smartmedicapp.dataLayer.ServisDatabase
import com.example.smartmedicapp.databinding.FragmentContantInfoBinding



/**
 * A simple [Fragment] subclass.
 * Use the [FragmentContantInfo.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentContantInfo : Fragment() {


    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? ,
        savedInstanceState : Bundle?
    ) : View? {

        // Inflate the layout for this fragment

        val binding: FragmentContantInfoBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_contant_info, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = FragmentContantInfoArgs.fromBundle(requireArguments())

        //get a database instance
        val dataSource = ServisDatabase.getInstance(application).servisDatabaseDao
        val viewModelFactory = ContactInfoViewModelFactory(arguments.ticketId, dataSource )

        // get a viewModel
        val contactInfoViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(ContactInfoViewModel::class.java)


        //bind viewModel
        binding.contactInfoViewModel =  contactInfoViewModel
        binding.lifecycleOwner = this


        // get a inputs field from binding
        val contactName = binding.editTextContactName
        val contactEmail = binding.edittextContactEmail
        val contactPhone = binding.edittextContactPhone
        val contactAddressPickup = binding.edittextAdressPickup

        //set listener for texh change and add validation proccess

        contactName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // after text is changed we validate form
                contactInfoViewModel.validateForm()
                if (!contactInfoViewModel.isNameValid()){
                    contactName.setError(getString(R.string.input_error_short_text))
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        //set listener for texh change and add validation proccess

        contactEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // after text is changed we validate form
                contactInfoViewModel.validateForm()
                if (!contactInfoViewModel.isEmailValid()){
                    contactEmail.setError(getString(R.string.input_error_invalid_email))
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        //set listener for texh change and add validation proccess
        contactPhone.addTextChangedListener(object : TextWatcher {
            // after text is changed we validate form
            override fun afterTextChanged(s: Editable) {
                contactInfoViewModel.validateForm()
                if (!contactInfoViewModel.isPhoneValid()){
                    contactPhone.setError(getString(R.string.input_error_invalid_number))
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        //set listener for texh change and add validation proccess
        contactAddressPickup.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                contactInfoViewModel.validateForm()
                if (!contactInfoViewModel.isAddressValid()){
                    contactAddressPickup.setError(getString(R.string.input_error_short_text))
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        // observing model variable to know if we are able to navigate further in application
        contactInfoViewModel.navigateToServis.observe(viewLifecycleOwner, Observer { ticket ->
            ticket?.let {

                this.findNavController().navigate(
                    FragmentContantInfoDirections.actionContantInfoFragmentToFragmentServis())
                Toast.makeText(this.requireContext(),  getString(R.string.ticket_created) , Toast.LENGTH_LONG).show()
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change
                contactInfoViewModel.doneNavigating()
            }
        })

        return binding.root

    }


}