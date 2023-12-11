package com.selin.fooddeliveryapp.ui.signIn
import com.selin.fooddeliveryapp.R

enum class SignInError {
    FILL_IN_THE_BLANKS,
    MIN_PASSWORD_LENGTH,
    INVALID_EMAIL_ADDRESS,
    EMAIL_ALREADY_IN_USE,
    CHECK_INFORMATION;


    companion object {
        fun toStringResource(error: SignInError): Int {
            return when (error) {
                FILL_IN_THE_BLANKS -> R.string.fill_blanks_text
                MIN_PASSWORD_LENGTH -> R.string.password_insufficient
                INVALID_EMAIL_ADDRESS -> R.string.ınvalid_alert
                EMAIL_ALREADY_IN_USE -> R.string.this_mail_is_in_use
                CHECK_INFORMATION -> R.string.check_information
            }
        }
    }
}