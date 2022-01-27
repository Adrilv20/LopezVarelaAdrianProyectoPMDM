package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities

import android.content.Context
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.App.GLB_STATE
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R

class UserData(
    val name: String,
    @Expose(serialize = true) @SerializedName("email") val email: String,
    @Expose(serialize = true) @SerializedName("password") val password: String,
    val dupPassword: String) {
    private companion object VALIDATION_UTILS {
        // should these values be configurable through a resources file rather than here?
        val MIN_CHARS_PASSWORD: Int = 6
        val MAX_CHARS_PASSWORD: Int = 16
        // TODO(see how to move these strings to resource files)
        val FIELD_NAME = GLB_STATE.context.getString(R.string.usernameFieldName)
        val FIELD_EMAIL = GLB_STATE.context.getString(R.string.emailFieldName)
        val FIELD_PASSWORD = GLB_STATE.context.getString(R.string.passwordFieldName)
        val EMPTY_FIELD_MESSAGE = GLB_STATE.context.getString(R.string.registration_error_empty_field)
        val NON_MATCHING_PASSWORDS_MESSAGE = GLB_STATE.context.getString(R.string.registration_error_non_matching_passwords)
        val NON_VALID_EMAIL_MESSAGE= GLB_STATE.context.getString(R.string.registration_error_non_valid_email)
        val NOT_ENOUGH_CHARS_PASSWORD_MESSAGE = GLB_STATE.context.getString(R.string.registration_error_min_chars_password, MIN_CHARS_PASSWORD)
        val TOO_MANY_CHARS_PASSWORD = GLB_STATE.context.getString(R.string.registration_error_max_chars_password, MAX_CHARS_PASSWORD)
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
        } else if (password.length > MAX_CHARS_PASSWORD) {
            errorMessage = TOO_MANY_CHARS_PASSWORD
        } else if (password != dupPassword) {
            errorMessage = NON_MATCHING_PASSWORDS_MESSAGE
        }

        return errorMessage == null
    }
}