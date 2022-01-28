package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.UserData
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityRegisterBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.KEYS
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var btnSignup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.registerActTitle)

        // set the process of data validation when pressing Sign button
        btnSignup = binding.btnSignUp
        btnSignup.setOnClickListener {
            val username: String;
            val email: String;
            val password: String;
            val duplicatedPassword: String
            // extract the input values from the textInputLayouts
            with(binding) {
                // IMPORTANT:  the text itself is within editText.text.
                // Since this can be a null value, we must access it either with !!. or !?.
                username = tilUsername.editText?.text.toString().trim()
                email = tilEmail.editText?.text.toString().trim()
                password = tilPassword.editText?.text.toString().trim()
                duplicatedPassword = tilPassword2.editText?.text.toString().trim()
            }
            // create the corresponding UserData object, that will handle validation
            val user = UserData(username, email, password, duplicatedPassword)

            if (user.isValidData()) {
                // prepare call to the backend to sing up the user
                // TODO change button to "loading" icon
                val singupCall = RetrofitClient.instance.userSingUp(user)
                singupCall.enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            // sing up was successful
                            // Store username and password on sharedPreferences
                            val sharedPref = getSharedPreferences(KEYS.LOGIN_DATA, MODE_PRIVATE)
                            with(sharedPref.edit()) {
                                putString(KEYS.EMAIL, user.email)
                                apply()
                            }
                            // go back to the login screen
                            onBackPressed()
                        } else {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Server side error while trying to sing up.",
                                Toast.LENGTH_LONG
                            ).show()
                            return
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Unexpected error during sinup:" + t.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                        return
                    }
                })
            } else {
                // error message in case the user info doesn't pass the local validation
                Toast.makeText(this, user.getErrorMessage(), Toast.LENGTH_LONG).show()
            }
        }
    }
}