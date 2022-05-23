package com.example.integralmed.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.example.integralmed.R
import com.example.integralmed.firestore.FirestoreClass
import com.example.integralmed.models.User
import com.example.integralmed.utils.Constants


class UserProfileActivity : BaseActivity(), View.OnClickListener {

    private lateinit var myUserDetails: User

    private lateinit var text_view_user_profile_title_header: TextView
    private lateinit var image_view_user_logo: ImageView
    private lateinit var edit_text_user_profile_first_name: EditText
    private lateinit var edit_text_user_profile_last_name: EditText
    private lateinit var edit_text_user_profile_email: EditText
    private lateinit var edit_text_user_profile_phone: EditText
    private lateinit var button_user_profile_save: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        text_view_user_profile_title_header = findViewById(R.id.text_view_user_profile_title_header)
        image_view_user_logo = findViewById(R.id.image_view_user_logo)
        edit_text_user_profile_first_name = findViewById(R.id.edit_text_user_profile_first_name)
        edit_text_user_profile_last_name = findViewById(R.id.edit_text_user_profile_last_name)
        edit_text_user_profile_email = findViewById(R.id.edit_text_user_profile_email)
        edit_text_user_profile_phone = findViewById(R.id.edit_text_user_profile_phone)
        button_user_profile_save = findViewById(R.id.button_user_profile_save)

        image_view_user_logo.setOnClickListener(this)
        button_user_profile_save.setOnClickListener(this)

        if (intent.hasExtra(Constants.EXTRA_USERS_DETAILS)) {
            myUserDetails = intent.getParcelableExtra(Constants.EXTRA_USERS_DETAILS)!!
        }
        edit_text_user_profile_first_name.setText(myUserDetails.firstName)
        edit_text_user_profile_last_name.setText(myUserDetails.lastName)
        edit_text_user_profile_email.setText(myUserDetails.email)
        edit_text_user_profile_email.isEnabled = false

        if (myUserDetails.profileCompleted == 0) {
            edit_text_user_profile_first_name.isEnabled = false
            edit_text_user_profile_last_name.isEnabled = false
            text_view_user_profile_title_header.text = resources.getText(R.string.complete_profile)
        } else {
            text_view_user_profile_title_header.text = resources.getText(R.string.edit_profile)
            if (myUserDetails.mobile != 0L) {
                edit_text_user_profile_phone.setText(myUserDetails.mobile.toString())
            }
        }
    }

    private fun validateUserProfileDetails(): Boolean {
        return when {
            TextUtils.isEmpty(
                edit_text_user_profile_first_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_first_name), true)
                false
            }
            TextUtils.isEmpty(
                edit_text_user_profile_last_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_last_name), true)
                false
            }
            TextUtils.isEmpty(
                edit_text_user_profile_phone.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_phone), true)
                false
            }
            else -> {
                true
            }
        }
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.button_user_profile_save -> {
                    if (validateUserProfileDetails()) {
                        showProgressDialog(resources.getString(R.string.please_wait))
                        updateUserProfileDetails()
                    }
                }
                R.id.image_view_user_logo -> {

                }
            }
        }
    }

    private fun updateUserProfileDetails() {
        val userHashMap = HashMap<String, Any>()
        val firstName = edit_text_user_profile_first_name.text.toString().trim { it <= ' ' }
        val lastName = edit_text_user_profile_last_name.text.toString().trim { it <= ' ' }
        val phone = edit_text_user_profile_phone.text.toString().trim { it <= ' ' }

        if (firstName != myUserDetails.firstName) {
            userHashMap[Constants.FIRST_NAME] = firstName
        }
        if (lastName != myUserDetails.lastName) {
            userHashMap[Constants.LAST_NAME] = lastName
        }
        if (phone != myUserDetails.mobile.toString() && phone.isNotEmpty()) {
            userHashMap[Constants.MOBILE] = phone.toLong()
        }
        userHashMap[Constants.COMPLETE_PROFILE] = 1
        FirestoreClass().updateUserProfileData(this, userHashMap)
    }

    fun userProfileUpdateSuccess() {
        hideProgressDialog()
        Toast.makeText(
            this@UserProfileActivity,
            resources.getString(R.string.msg_profile_updatins_success), Toast.LENGTH_SHORT
        )
        startActivity(Intent(this@UserProfileActivity, MainActivity::class.java))
        finish()
    }
}