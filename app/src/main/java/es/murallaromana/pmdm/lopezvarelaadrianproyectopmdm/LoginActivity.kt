package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var btnRegister : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnRegister = binding.btnRegister
        btnRegister.setOnClickListener {
            Toast.makeText(this, "Redirigiendo a la pantalla de registro.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }

}