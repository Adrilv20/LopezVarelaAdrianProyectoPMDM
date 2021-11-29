package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities

import android.content.Context

class UserData(val name: String, val email: String, val password: String, val dupPassword: String) {
    private companion object VALIDATION_UTILS {
        // should these values be configurable through a resources file rather than here?
        val MIN_CHARS_PASSWORD: Int = 6
        val MAX_CHARS_PASSWORD: Int = 16
        // TODO(see how to move these strings to resource files)
        val FIELD_NAME = "susername"
        val FIELD_EMAIL = "email"
        val FIELD_PASSWORD = "password"
        val EMPTY_FIELD_MESSAGE = "The field %s can't be empty."
        val NON_MATCHING_PASSWORDS_MESSAGE = "The introduced passwords don't match"
        val NON_VALID_EMAIL_MESSAGE= "The given email is not valid."
        val NOT_ENOUGH_CHARS_PASSWORD_MESSAGE = "The password needs at least ${MIN_CHARS_PASSWORD} characters."
        val TOO_MANY_CHARS_PASSWORD = "The password can't have more than ${MAX_CHARS_PASSWORD} characters."
        val EMAIL_REGEX: Regex = Regex("^[A-ZÑa-zñ0-9+_.-]+@[a-zñ0-9.-]+$")
    }

    private var errorMessage: String? = null

    fun getErrorMessage() : String? = errorMessage

    fun isValidData(): Boolean {
        errorMessage = null

        if (name.isEmpty()) {
            errorMessage = EMPTY_FIELD_MESSAGE.format(FIELD_NAME)
        } else if (email.isEmpty()) {
            errorMessage = EMPTY_FIELD_MESSAGE.format(FIELD_EMAIL)
        } else if (!email.matches(EMAIL_REGEX)) {
            errorMessage = NON_VALID_EMAIL_MESSAGE
        } else if (password.isEmpty()) {
            errorMessage = EMPTY_FIELD_MESSAGE.format(FIELD_PASSWORD)
        } else if (password.length < MIN_CHARS_PASSWORD) {
            errorMessage = NOT_ENOUGH_CHARS_PASSWORD_MESSAGE
        } else if (password.length < MIN_CHARS_PASSWORD) {
            errorMessage = TOO_MANY_CHARS_PASSWORD
        } else if (password != dupPassword) {
            errorMessage = NON_MATCHING_PASSWORDS_MESSAGE
        }

        return errorMessage == null
    }
}