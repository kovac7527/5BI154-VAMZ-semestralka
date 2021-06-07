package com.example.smartmedicapp.app_ui.ticket.contactInfo

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

        val dataSource = ServisDatabase.getInstance(application).servisDatabaseDao
        val viewModelFactory = ContactInfoViewModelFactory(arguments.ticketId, dataSource )

        val contactInfoViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(ContactInfoViewModel::class.java)

        binding.contactInfoViewModel =  contactInfoViewModel
        binding.lifecycleOwner = this

        val contactName = binding.editTextContactName
        val contactEmail = binding.edittextContactEmail
        val contactPhone = binding.edittextContactPhone
        val contactAddressPickup = binding.edittextAdressPickup

        contactName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                contactInfoViewModel.validateForm()
                if (!contactInfoViewModel.isNameValid()){
                    contactName.setError("At least 3 characters")
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })



        contactEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                contactInfoViewModel.validateForm()
                if (!contactInfoViewModel.isEmailValid()){
                    contactEmail.setError("At least 3 characters")
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        contactPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                contactInfoViewModel.validateForm()
                if (!contactInfoViewModel.isPhoneValid()){
                    contactPhone.setError("At least 3 characters")
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        contactAddressPickup.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                contactInfoViewModel.validateForm()
                if (!contactInfoViewModel.isAddressValid()){
                    contactAddressPickup.setError("At least 3 characters")
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })








        contactInfoViewModel.navigateToServis.observe(viewLifecycleOwner, Observer { ticket ->
            ticket?.let {
                // We need to get the navController from this, because button is not ready, and it
                // just has to be a view. For some reason, this only matters if we hit stop again
                // after using the back button, not if we hit stop and choose a quality.
                // Also, in the Navigation Editor, for Quality -> Tracker, check "Inclusive" for
                // popping the stack to get the correct behavior if we press stop multiple times
                // followed by back.
                // Also: https://stackoverflow.com/questions/28929637/difference-and-uses-of-oncreate-oncreateview-and-onactivitycreated-in-fra
                this.findNavController().navigate(
                    FragmentContantInfoDirections.actionContantInfoFragmentToFragmentServis())

                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change
                contactInfoViewModel.doneNavigating()
            }
        })





        return binding.root

    }


}