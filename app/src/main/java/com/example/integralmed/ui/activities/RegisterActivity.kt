package com.example.integralmed.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.example.integralmed.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        checkbox_terms_and_condition.setOnClickListener(this)
        button_registration.setOnClickListener(this)
        text_view_register_login.setOnClickListener(this)

    }

    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(
                edit_text_registration_first_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_first_name), true)
                false
            }
            TextUtils.isEmpty(
                edit_text_registration_last_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_last_name), true)
                false
            }
            TextUtils.isEmpty(edit_text_registration_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_email), true)
                false
            }
            TextUtils.isEmpty(
                edit_text_registration_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_password), true)
                false
            }
            TextUtils.isEmpty(
                edit_text_registration_confirm_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_confirm_password), true)
                false
            }
            edit_text_registration_password.text.toString()
                .trim { it <= ' ' } != edit_text_registration_confirm_password.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(resources.getString(R.string.error_password_mismatch), true)
                false
            }
            !checkbox_terms_and_condition.isChecked -> {
                showErrorSnackBar(
                    resources.getString(R.string.error_check_box_terms_and_condition),
                    true
                )
                false
            }
            else -> {
                showErrorSnackBar(resources.getString(R.string.error_details_valid), false)
                true
            }
        }
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.button_registration -> {
                    validateRegisterDetails()
                }
                R.id.text_view_register_login -> {
                    onBackPressed()
                }
            }
        }
    }
}