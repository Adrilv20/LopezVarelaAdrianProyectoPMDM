package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityLoginBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.LoginData
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Token
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.KEYS
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.RetrofitClient
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var btnRegister : Button
    private lateinit var btnLogin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // if there's an authToken stored, skip straight to the list of movies
        SessionManager.fetchAuthToken()?.let {
            goToListOfFilms()
        }

        // "else" sets up the login screen
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // make the register button redirect to the corresponding screen
        btnRegister = binding.btnRegister
        btnRegister.setOnClickListener {
            disableButtons()
            startActivity(Intent(this, RegisterActivity::class.java))
            enableButtons() // not fully sure if needed, leaving it just in case
        }

        // make the login button transition into the list of films
        // no actual account check performed since this is mostly a mock application
        btnLogin = binding.btnLogin
        btnLogin.setOnClickListener {
            disableButtons()
            val loginCall =
                RetrofitClient.instance
                .userLogIn(LoginData(binding.etEmail.text.toString().trim(), binding.etPassword.text.toString().trim()))

            loginCall.enqueue(object : Callback<Token> {
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    enableButtons()
                    if (response.isSuccessful) {
                        val token = response.body() as Token
                        SessionManager.saveAuthToken(token.token)
                        goToListOfFilms()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Server side error while trying to log in.",
                            Toast.LENGTH_LONG
                        ).show()
                        return
                    }
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {
                    enableButtons()
                    Toast.makeText(
                        this@LoginActivity,
                        "Unexpected error during login:" + t.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }
            })
        }
    }

    override fun onResume() {
        // in case there's an account data stored in sharedPreferences from the register screen,
        // load the corresponding username and password on this screen's fields.
        val sharedPref = getSharedPreferences(KEYS.LOGIN_DATA, MODE_PRIVATE)
        val email: String = sharedPref.getString(KEYS.EMAIL, "").toString()
        binding.etEmail.setText(email)
        super.onResume()
    }

    private fun goToListOfFilms() {
        val filmsIntent = Intent(this@LoginActivity,ItemListActivity::class.java)
        filmsIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(filmsIntent)
        finish()
    }

    // methods used to disable/enable buttons to prevent weird interactions while pressing buttons repeatedly or
    // waiting for the server response
    private fun enableButtons(state : Boolean = true) {
        btnLogin.isEnabled = state
        btnRegister.isEnabled = state
    }

    private fun disableButtons() {
        enableButtons(state = false)
    }
}