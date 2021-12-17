package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.UserData
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityRegisterBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.KEYS

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var btnSignup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.registerActTitle)

        // set the process of data validation when pressing Sign button
        btnSignup = binding.btnSignUp
        btnSignup.setOnClickListener {
            val username : String; val email: String; val password: String; val duplicatedPassword : String
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
                // Store username and password on sharedPreferences
                val sharedPref = getSharedPreferences(KEYS.LOGIN_DATA, MODE_PRIVATE)
                with (sharedPref.edit()) {
                    putString(KEYS.USERNAME, user.name)
                    putString(KEYS.PASSWORD, user.password)
                    apply()
                }
                // go back to the login screen
                onBackPressed()
            } else {
                Toast.makeText(this, user.getErrorMessage(), Toast.LENGTH_LONG).show()
            }
        }
    }
}