package com.selin.fooddeliveryapp.ui.signUp

import com.selin.fooddeliveryapp.R

enum class SignUpError {
    FILL_IN_THE_BLANKS,
    MIN_PASSWORD_LENGTH,
    DIFFERENT_PASSWORD,
    INVALID_EMAIL_ADDRESS,
    EMAIL_ALREADY_IN_USE;

    companion object {
        fun toStringResource(error: SignUpError): Int {
            return when (error) {
                FILL_IN_THE_BLANKS -> R.string.fill_blanks_text
                MIN_PASSWORD_LENGTH -> R.string.password_insufficient
                DIFFERENT_PASSWORD -> R.string.password_is_not_the_same
                INVALID_EMAIL_ADDRESS -> R.string.ınvalid_alert
                EMAIL_ALREADY_IN_USE -> R.string.this_mail_is_in_use
            }
        }
    }
}