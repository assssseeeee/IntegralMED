package com.example.integralmed.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.integralmed.R
import com.example.integralmed.firestore.FirestoreClass
import com.example.integralmed.models.User
import com.example.integralmed.utils.Constants
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var text_view_forgot_password: TextView
    private lateinit var text_view_register: TextView
    private lateinit var button_login: Button
    private lateinit var edit_text_email: EditText
    private lateinit var edit_text_password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        text_view_forgot_password = findViewById(R.id.text_view_forgot_password)
        text_view_register = findViewById(R.id.text_view_register)
        button_login = findViewById(R.id.button_login)
        edit_text_email = findViewById(R.id.edit_text_email)
        edit_text_password = findViewById(R.id.edit_text_password)

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

    private fun loginRegisteredUser() {
        if (validateLoginDetails()) {
            showProgressDialog(resources.getString(R.string.please_wait))
            val email: String = edit_text_email.text.toString().trim { it <= ' ' }
            val password: String = edit_text_password.text.toString().trim { it <= ' ' }
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        FirestoreClass().getUserDetails(this@LoginActivity)
                    } else {
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }

    fun userLoggedSuccess(user: User) {
        hideProgressDialog()
        if (user.profileCompleted == 0) {
            val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USERS_DETAILS, user)
            startActivity(intent)
        } else {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
        finish()
    }


    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.text_view_register -> {
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
                R.id.button_login -> {
                    loginRegisteredUser()
                }
            }
        }
    }
}