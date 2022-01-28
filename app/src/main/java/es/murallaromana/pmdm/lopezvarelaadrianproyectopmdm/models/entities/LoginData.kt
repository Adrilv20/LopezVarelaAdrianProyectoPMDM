package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities

import com.google.gson.annotations.Expose

class LoginData(
    @Expose(serialize = true) val email : String,
    @Expose(serialize = true) val password : String
)