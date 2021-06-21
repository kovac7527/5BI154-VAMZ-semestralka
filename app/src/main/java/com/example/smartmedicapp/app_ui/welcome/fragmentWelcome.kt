package com.example.smartmedicapp.app_ui.welcome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
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


    /**
     * Hold a account of 3th party service
     */
    private lateinit var account: Auth0

    /**
     *Hold user credentials
     */
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

        // added simple navigation for interactive images

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

        // Creating a new Auth0 account
        account = Auth0(
            getString(R.string.com_auth0_client_id),
            getString(R.string.com_auth0_domain),
        )

        return binding.root
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    /**
     * Function which is responsible for start user validation via browser, here we are getting back JWT token
     */
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
                    // we save credentials to manager DI
                    CredentialsManager.saveCredentials(credentials)
                    Toast.makeText(requireContext(),  getString(R.string.login_successfull) , Toast.LENGTH_LONG).show()

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

    /**
     * Function which enables user to logout
     */
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



}