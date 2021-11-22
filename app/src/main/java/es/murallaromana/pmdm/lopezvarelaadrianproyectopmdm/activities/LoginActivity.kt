package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
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
    }

    override fun onResume() {
        val sharedPref = getSharedPreferences(KEYS.LOGIN_DATA, MODE_PRIVATE)
        val username: String? = sharedPref.getString(KEYS.USERNAME, "").toString()
        val password: String? = sharedPref.getString(KEYS.PASSWORD, "").toString()
        binding.etUsername.setText(username)
        binding.etPassword.setText(password)
        super.onResume()
    }
}