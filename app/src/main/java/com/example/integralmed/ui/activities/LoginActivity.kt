package com.example.integralmed.ui.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.example.integralmed.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        text_view_forgot_password.setOnClickListener(this)
        text_view_register.setOnClickListener(this)
        button_login.setOnClickListener(this)
    }

    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(edit_text_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_email), true)
                false
            }
            TextUtils.isEmpty(edit_text_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_password), true)
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
                R.id.text_view_register -> {
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
                R.id.button_login -> {
                    validateLoginDetails()
                }
            }
        }
    }
}