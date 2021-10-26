package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm

class UserData(val name: String, val email: String, val password: String, val dupPassword : String?) {

    fun isValidData() : Boolean {
        return !name.isEmpty() && !email.isEmpty() && !password.isEmpty() && password == dupPassword
    }
}