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
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // make the register button redirect to the corresponding screen
        btnRegister = binding.btnRegister
        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // make the login button transition into the list of films
        // no actual account check performed since this is mostly a mock application
        btnLogin = binding.btnLogin
        btnLogin.setOnClickListener {
            val loginCall =
                RetrofitClient.instance
                .userLogIn(LoginData(binding.etEmail.text.toString().trim(), binding.etPassword.text.toString().trim()))

            loginCall.enqueue(object : Callback<Token> {
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    if (response.isSuccessful) {
                        val token = response.body() as Token
                        SessionManager.saveAuthToken(token.token)
                        startActivity(Intent(this@LoginActivity,ItemListActivity::class.java))
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
        // TODO FULLY CHANGE LOGIN TO WORK WITH EMAIL INSTEAD OF USERNANE. Right now just slightly adapted to test api calls
        val sharedPref = getSharedPreferences(KEYS.LOGIN_DATA, MODE_PRIVATE)
        val email: String? = sharedPref.getString(KEYS.EMAIL, "").toString()
        binding.etEmail.setText(email)
        super.onResume()
    }
}