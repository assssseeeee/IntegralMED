package com.example.integralmed.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.integralmed.R
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)


        edit_text_user_profile_first_name
        edit_text_user_profile_last_name
        edit_text_user_profile_phone
        button_user_profile_save.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}