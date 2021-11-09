package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities

class UserData(val name: String, val email: String, val password: String, val dupPassword : String?) {

    fun isValidData() : Boolean {
        return name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && password == dupPassword
    }
}