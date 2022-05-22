package com.example.integralmed.ui.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.example.integralmed.R
import com.example.integralmed.firestore.FirestoreClass
import com.example.integralmed.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegisterActivity : BaseActivity(), View.OnClickListener {

    private lateinit var checkbox_terms_and_condition: CheckBox
    private lateinit var button_registration: Button
    private lateinit var text_view_register_login: TextView
    private lateinit var edit_text_registration_first_name: EditText
    private lateinit var edit_text_registration_last_name: EditText
    private lateinit var edit_text_registration_email: EditText
    private lateinit var edit_text_registration_password: EditText
    private lateinit var edit_text_registration_confirm_password: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        text_view_register_login = findViewById(R.id.text_view_register_login)
        edit_text_registration_first_name = findViewById(R.id.edit_text_registration_first_name)
        edit_text_registration_last_name = findViewById(R.id.edit_text_registration_last_name)
        edit_text_registration_email = findViewById(R.id.edit_text_registration_email)
        edit_text_registration_password = findViewById(R.id.edit_text_registration_password)
        edit_text_registration_confirm_password =
            findViewById(R.id.edit_text_registration_confirm_password)
        checkbox_terms_and_condition = findViewById(R.id.checkbox_terms_and_condition)
        button_registration = findViewById(R.id.button_registration)

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
                        onBackPressed()
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