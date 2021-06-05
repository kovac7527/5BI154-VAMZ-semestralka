package com.example.smartmedicapp.app_ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.example.smartmedicapp.CredentialsManager.CredentialsManager
import com.example.smartmedicapp.LoggedActivity
import com.example.smartmedicapp.R
import com.example.smartmedicapp.databinding.FragmentWelcomeBinding
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_welcome.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragmentWelcome : Fragment() {
    private lateinit var account: Auth0
    private var cachedCredentials: Credentials? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentWelcomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_welcome, container, false)

        val application = requireNotNull(this.activity).application

        binding.setLifecycleOwner(this)
        binding.imageEarth.setOnClickListener {
                view : View ->
            view.findNavController().navigate(R.id.action_fragment_welcome_to_fragment_ecology)
        }
        binding.imageEuro.setOnClickListener {
                view : View ->
            view.findNavController().navigate(R.id.action_fragment_welcome_to_fragment_economy)
        }
        binding.imageTimer.setOnClickListener {
                view : View ->
            view.findNavController().navigate(R.id.action_fragment_welcome_to_fragment_fast2)
        }
        binding.buttonLogin.setOnClickListener {
            loginWithBrowser()
              //  view : View ->
            //view.findNavController().navigate(R.id.action_fragment_welcome_to_fragmentLogin)
        }
        binding.buttonLogout.setOnClickListener {
            logout()
            //  view : View ->
            //view.findNavController().navigate(R.id.action_fragment_welcome_to_fragmentLogin)
        }
        account = Auth0(
            "E3jXEWPwhz7DbDrP5t2GND0HRXEb05mS",
            "dev-dpmumsxt.eu.auth0.com"
        )

        return binding.root
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun loginWithBrowser() {
        // Setup the WebAuthProvider, using the custom scheme and scope.

        WebAuthProvider.login(account)
            .withScheme(getString(R.string.com_auth0_scheme))
            .withScope("openid profile email read:current_user update:current_user_metadata")
            .withAudience("https://${getString(R.string.com_auth0_domain)}/api/v2/")

            // Launch the authentication passing the callback where the results will be received
            .start(this.requireActivity(), object : Callback<Credentials, AuthenticationException> {
                // Called when there is an authentication failure
                override fun onFailure(exception: AuthenticationException) {
                    Timber.i("OAuth login : FAIL  "+ exception.getDescription() + " " + exception.toString())

                    // Something went wrong!
                }

                // Called when authentication completed successfully
                override fun onSuccess(credentials: Credentials) {

                    CredentialsManager.saveCredentials(credentials)

                    // Get the access token from the credentials object.
                    // This can be used to call APIs
                    cachedCredentials = credentials
                    Timber.i("OAuth login : SUCESS ${credentials.accessToken}")
                    val accessToken = credentials.accessToken
                    val intent = Intent (getActivity(), LoggedActivity::class.java)
                    intent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                                Intent.FLAG_ACTIVITY_NEW_TASK
                    )
                    requireActivity().startActivity(intent)
                }
            })
    }


    private fun logout() {
        WebAuthProvider.logout(account)
            .withScheme("demo")
            .start(this.requireActivity(), object: Callback<Void?, AuthenticationException> {
                override fun onSuccess(payload: Void?) {
                    Timber.i("OAuth logout : The user has been logged out!")
                    // The user has been logged out!
                }

                override fun onFailure(error: AuthenticationException) {
                    Timber.i("OAuth logout :Something went wrong!" + error.message)
                    // Something went wrong!
                }
            })
    }

    private fun showUserProfile() {
        val client = AuthenticationAPIClient(account)

        // Use the access token to call userInfo endpoint.
        // In this sample, we can assume cachedCredentials has been initialized by this point.
        client.userInfo(cachedCredentials!!.accessToken!!)
            .start(object : Callback<UserProfile, AuthenticationException> {
                override fun onFailure(exception: AuthenticationException) {
                    Timber.i("Failure: ${exception.getCode()}")
                }

                override fun onSuccess(profile: UserProfile) {
                    Timber.i("Name: ${profile.name}  Email : ${profile.email}")

                }
            })
    }



}