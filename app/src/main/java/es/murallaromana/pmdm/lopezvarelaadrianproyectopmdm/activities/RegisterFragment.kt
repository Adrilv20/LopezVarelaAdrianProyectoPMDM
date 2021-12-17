package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.FragmentRegisterBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.UserData
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.KEYS

class RegisterFragment : Fragment() {
    private lateinit var binding : FragmentRegisterBinding
    private lateinit var btnSignup: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)

        activity?.title = getString(R.string.registerActTitle)

        // set the process of data validation when pressing Sign button
        btnSignup = binding.btnSignUp
        btnSignup.setOnClickListener {
            val username : String; val email: String; val password: String; val duplicatedPassword : String
            // extract the input values from the textInputLayouts
            with(binding) {
                // IMPORTANT:  the text itself is within editText.text.
                // Since this can be a null value, we must access it either with !!. or !?.
                username = tilUsername.editText?.text.toString().trim()
                email = tilEmail.editText?.text.toString().trim()
                password = tilPassword.editText?.text.toString().trim()
                duplicatedPassword = tilPassword2.editText?.text.toString().trim()
            }
            // create the corresponding UserData object, that will handle validation
            val user = UserData(username, email, password, duplicatedPassword)

            if (user.isValidData()) {
                // Store username and password on sharedPreferences
                val sharedPref = activity?.getSharedPreferences(
                    KEYS.LOGIN_DATA,
                    AppCompatActivity.MODE_PRIVATE
                )
                with (sharedPref!!.edit()) {
                    putString(es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.KEYS.USERNAME, user.name)
                    putString(es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.KEYS.PASSWORD, user.password)
                    apply()
                }
                // go back to the login screen
                activity?.onBackPressed()
            } else {
                Toast.makeText(activity, user.getErrorMessage(), Toast.LENGTH_LONG).show()
            }
        }

        // Inflate the layout for this fragment. TODO(use binding instead)
        val views = inflater.inflate(R.layout.fragment_register, container, false)
        return views
    }
}