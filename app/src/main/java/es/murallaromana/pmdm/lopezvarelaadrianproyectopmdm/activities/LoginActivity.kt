package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var btnRegister : Button
    private lateinit var btnLogin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnRegister = binding.btnRegister
        btnRegister.setOnClickListener {
            Toast.makeText(this, "Redirigiendo a la pantalla de registro.", Toast.LENGTH_SHORT)
                .show()
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnLogin = binding.btnLogin
        btnLogin.setOnClickListener {
            startActivity(Intent(this,ItemListActivity::class.java))
        }

        // TODO move this to the correct part of the lifecycle. Currently not loading when coming back from register screen.
        // load saved username and password from SharedPreferences
        // setting the default as "" so if there're no saved values, the fields show as empty

        // TODO FIX
       /* val sharedPref = getPreferences(MODE_PRIVATE)
        val username: String = sharedPref.getString(getString(R.string.username_key), "")
            .toString() // TODO ask why it needs the .toString
        val password: String = sharedPref.getString(getString(R.string.password_key), "").toString()
        Log.d(
            "Login",
            "Retrieved data from sharedpreferences:\n User: ${username}, Password: ${password}"
        )
        binding.etUsername.setText(username)
        binding.etPassword.setText(password)*/
    }
}