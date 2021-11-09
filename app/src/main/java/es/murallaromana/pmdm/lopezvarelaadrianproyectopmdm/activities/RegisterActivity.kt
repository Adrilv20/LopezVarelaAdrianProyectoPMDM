package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.UserData
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
                // IMPORTANT:  the text itself is within editText.text.
                // Since this can be a null value, we must access it either with !!. or !?.
                username = tilUsername.editText?.text.toString().trim()
                email = tilEmail.editText?.text.toString().trim()
                password = tilPassword.editText?.text.toString().trim()
                duplicatedPassword = tilPassword2.editText?.text.toString().trim()
            }
            val user = UserData(username, email, password, duplicatedPassword)

            if (user.isValidData()) {
                // Store info and go back to the login screen
                val sharedPref = getPreferences(MODE_PRIVATE)
                with (sharedPref.edit()) {
                    putString(getString(R.string.username_key), user.name)
                    putString(getString(R.string.password_key), user.password)
                    apply()
                }
                onBackPressed()
            } else {
                // TODO show toast to indicate invalid data
                Toast.makeText(this, "Some value is incorrect", Toast.LENGTH_LONG).show()
            }
        }
    }
}