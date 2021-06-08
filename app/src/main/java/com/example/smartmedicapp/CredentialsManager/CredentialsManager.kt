package com.example.smartmedicapp.CredentialsManager

import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import timber.log.Timber

/**
 * DI class that holds all information about currently loged in user
 */
object CredentialsManager {
    @Volatile private lateinit var account: Auth0
    @Volatile private var cachedCredentials: Credentials? = null
    @Volatile private lateinit var userProfile :UserProfile
    @Volatile public var IsLoggedIn :Boolean = false;

    init {
        account = Auth0(
            "E3jXEWPwhz7DbDrP5t2GND0HRXEb05mS",
            "dev-dpmumsxt.eu.auth0.com"
        )

    }

    /**
     * Save credentials provided by service
     */
    fun saveCredentials(credentials: Credentials) {
        cachedCredentials = credentials
        IsLoggedIn = true;
        resolveUserProfile()
    }


    /**
     * Resolve user profile
     */
    fun resolveUserProfile()  {

        val client = AuthenticationAPIClient(account)

        client.userInfo(cachedCredentials!!.accessToken!!)
            .start(object : Callback<UserProfile, AuthenticationException> {

                override fun onFailure(exception: AuthenticationException) {
                    Timber.i("Failure: ${exception.getCode()}")
                }
                override fun onSuccess(profile: UserProfile) {
                    Timber.i("User profile resolve succesfull: ${profile.name}")

                    userProfile = profile
                }
            })
    }

    /**
     * Returning Auth0 account
     */
    fun getAccount():Auth0 {
        return account;
    }

    /**
     * Return user profile usefull for user informations
     */
    fun getUserProfile() : UserProfile {
        return userProfile
    }


}