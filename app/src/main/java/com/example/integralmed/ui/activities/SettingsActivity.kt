package com.example.integralmed.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.integralmed.R
import com.example.integralmed.models.User

class SettingsActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    fun userDetailsSuccess(user: User) {

    }
}