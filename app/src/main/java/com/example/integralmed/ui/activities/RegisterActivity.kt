package com.example.integralmed.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.integralmed.R
import com.example.integralmed.firestore.FirestoreClass
import com.example.integralmed.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.concurrent.thread

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


    private fun registerUser() {
        if (validateRegisterDetails()) {
            showProgressDialog(resources.getString(R.string.please_wait))
            val email: String =
                edit_text_registration_email.text.toString().trim { it <= ' ' }
            val password: String =
                edit_text_registration_password.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {
                        val fireBaseUser: FirebaseUser = task.result!!.user!!
                        val user = User(
                            fireBaseUser.uid,
                            edit_text_registration_first_name.text.toString().trim { it <= ' ' },
                            edit_text_registration_last_name.text.toString().trim { it <= ' ' },
                            edit_text_registration_email.text.toString().trim { it <= ' ' }
                        )
                        FirestoreClass().registerUser(this@RegisterActivity, user)
                        FirebaseAuth.getInstance().signOut()
                        finish()
                    } else {
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                })
        }
    }

    fun userRegistrationSuccess() {
        hideProgressDialog()
        Toast.makeText(
            this@RegisterActivity,
            resources.getString(R.string.register_successful),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.button_registration -> {
                    registerUser()
                }
                R.id.text_view_register_login -> {
                    onBackPressed()
                }
            }
        }
    }
}