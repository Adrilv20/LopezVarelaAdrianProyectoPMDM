package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        supportFragmentManager.addOnBackStackChangedListener {
            supportActionBar?.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 0)
        }

        with(supportFragmentManager.beginTransaction()){
            add(R.id.landing_activity_layout, LoginFragment())
            commit()
        }

    }
}