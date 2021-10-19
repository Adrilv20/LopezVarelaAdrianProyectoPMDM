package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var btnSignup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnSignup = binding.btnSignUp
        btnSignup.setOnClickListener {
            val username : String; val email: String; val password: String; val duplicatedPassword : String
            with(binding) {
                username = tilUsername.editText.toString()
                email = tilEmail.editText.toString()
                password = tilPassword.editText.toString()
                duplicatedPassword = tilPassword2.editText.toString()
            }
            val user = UserData(username, email, password, duplicatedPassword)
            if (validateInput()) {
                // Store info and go back to the login screen
                Toast.makeText(this, "Successful sign up. You're being redirected to the login screen.", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }
    }

    /**
     * Quick validation of the fields requested. Strings must not be empty, and both passwords must match.
     * Country is left unused for now.
     * If fields are
     */
    private fun validateInput() : Boolean {
        va
    }
}