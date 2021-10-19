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
            if (validateInput()) {
                // Store info and go back to the login screen
                Toast.makeText(this, "Successful sign up. You're being redirected to the login screen.", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }
    }

    private fun validateInput() : Boolean {
        return true
    }
}