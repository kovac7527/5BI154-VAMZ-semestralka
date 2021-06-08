package com.example.smartmedicapp

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.example.smartmedicapp.CredentialsManager.CredentialsManager
import com.example.smartmedicapp.databinding.ActivityLoggedBinding
import com.google.android.material.navigation.NavigationView
import timber.log.Timber

/**
 * Activity launched after users succesfull login
 */

class LoggedActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener   {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityLoggedBinding>(this,
            R.layout.activity_logged)

        drawerLayout = binding.drawerLayout

        val navController = findNavController(R.id.nav_log_fragment)

       NavigationUI.setupActionBarWithNavController(this,navController, drawerLayout)


        NavigationUI.setupWithNavController(binding.navView, navController)

        binding.navView.setNavigationItemSelectedListener(this)



    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_log_fragment)
        return NavigationUI.navigateUp(navController, drawerLayout)

    }


    /**
     * Function to handle menu interactions
     */

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.menu_logout -> {
                // Handle the logout
                Timber.i("it Works")
                    // logout currently logged user by webauthprovider
                    WebAuthProvider.logout(CredentialsManager.getAccount())
                        .withScheme("demo")
                        .start(this, object:
                            Callback<Void?, AuthenticationException> {
                            override fun onSuccess(payload: Void?) {
                                Timber.i("OAuth logout : The user has been logged out!")

                                val res: Resources = resources
                                throwToast(  resources.getString(R.string.logout_successfull))
                                returnToLogin()
                                CredentialsManager.IsLoggedIn = false;

                            }

                            override fun onFailure(error: AuthenticationException) {
                                Timber.i("OAuth logout :Something went wrong!" + error.message)
                                val res: Resources = resources
                                throwToast(  resources.getString(R.string.logout_fail))

                            }
                        })


            }
            R.id.fragmentServis-> {
                // when pressed servis
                findNavController(R.id.nav_log_fragment).navigate(R.id.action_fragmentMain_to_fragmentServis)

            }
            R.id.fragmentServisHistory-> {
                // when pressed servis history
                findNavController(R.id.nav_log_fragment).navigate(R.id.action_fragmentMain_to_fragmentServisHistory)

            }
            R.id.fragmentAboutUs-> {
                // when pressed about us
                findNavController(R.id.nav_log_fragment).navigate(R.id.action_fragmentMain_to_fragmentAboutUs)

            }
            R.id.fragmentWaterProofTest-> {
                // when pressed waterProofTest
                findNavController(R.id.nav_log_fragment).navigate(R.id.action_fragmentMain_to_fragmentWaterProofTest)

            }


        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun throwToast (text : String) {
        Toast.makeText(applicationContext,  text ,Toast.LENGTH_LONG).show()
    }

    /**
     * Returning to login screen
     */
    private fun returnToLogin () {
        // back to main acitvity clear backstack
        val intent = Intent (this, MainActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK
        )
        startActivity(intent)
    }
}

