package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityLoginBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding
    private lateinit var btnRegister : Button
    private lateinit var btnLogin : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)


        // make the register button redirect to the corresponding screen
        btnRegister = binding.btnRegister
        btnRegister.setOnClickListener {
            with(parentFragmentManager.beginTransaction()) {
                replace(R.id.landing_activity_layout, RegisterFragment())
                addToBackStack(null)
                commit()
            }
        }

        // make the login button transition into the list of films
        // no actual account check performed since this is mostly a mock application
        btnLogin = binding.btnLogin
        btnLogin.setOnClickListener {
            startActivity(Intent(activity,ItemListActivity::class.java))
        }

        // Inflate the layout for this fragment. TODO(use binding instead)
        val views = inflater.inflate(R.layout.fragment_login, container, false)
        return views
    }


}