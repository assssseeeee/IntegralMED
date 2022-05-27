package com.example.integralmed.ui.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.integralmed.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : BaseActivity() {

    private lateinit var button_forgot_password_submit: Button
    private lateinit var edit_text_forgot_password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        button_forgot_password_submit = findViewById(R.id.button_forgot_password_submit)
        edit_text_forgot_password = findViewById(R.id.edit_text_forgot_password)

        button_forgot_password_submit.setOnClickListener {

            val email =
                edit_text_forgot_password.text.toString().trim { it <= ' ' }
            if (email.isEmpty()) {
                showErrorSnackBar(resources.getString(R.string.error_email), true)
            } else {
                showProgressDialog(resources.getString(R.string.please_wait))

                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        hideProgressDialog()
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                resources.getString(R.string.forgot_email_success),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }
            }
        }
    }
}