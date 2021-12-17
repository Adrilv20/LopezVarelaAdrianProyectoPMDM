package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityLoginBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.KEYS

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var btnRegister : Button
    private lateinit var btnLogin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // make the register button redirect to the corresponding screen
        btnRegister = binding.btnRegister
        btnRegister.setOnClickListener {
            Toast.makeText(this, getString(R.string.redirect_to_login_message), Toast.LENGTH_SHORT)
                .show()
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // make the login button transition into the list of films
        // no actual account check performed since this is mostly a mock application
        btnLogin = binding.btnLogin
        btnLogin.setOnClickListener {
            startActivity(Intent(this,ItemListActivity::class.java))
        }
    }

    override fun onResume() {
        // in case there's an account data stored in sharedPreferences from the register screen,
        // load the corresponding username and password on this screen's fields.
        val sharedPref = getSharedPreferences(KEYS.LOGIN_DATA, MODE_PRIVATE)
        val username: String? = sharedPref.getString(KEYS.USERNAME, "").toString()
        val password: String? = sharedPref.getString(KEYS.PASSWORD, "").toString()
        binding.etUsername.setText(username)
        binding.etPassword.setText(password)
        super.onResume()
    }
}