package com.example.integralmed.ui.activities

import android.app.Dialog
import android.os.Build
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.integralmed.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay


open class BaseActivity : AppCompatActivity() {
    private lateinit var myShowProgressDialog: Dialog
    private var doubleBackToExitPressedOnce = false

    fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if (errorMessage) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.color_snackBar_error
                )
            )
        } else {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.color_snackBar_success
                )
            )
        }
        snackBar.show()
    }


    fun showProgressDialog(text: String) {
        myShowProgressDialog = Dialog(this)
        myShowProgressDialog.setContentView(R.layout.dialog_progress)
        myShowProgressDialog.findViewById<TextView>(R.id.text_view_progress_text).text = text
        myShowProgressDialog.setCancelable(false)
        myShowProgressDialog.setCanceledOnTouchOutside(false)
        myShowProgressDialog.show()
    }


    fun hideProgressDialog() {
        myShowProgressDialog.dismiss()
    }

    fun hideStatusBar() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    fun doubleBackToExit() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(
            this,
            resources.getString(R.string.double_exit_message),
            Toast.LENGTH_LONG
        ).show()
        @Suppress("DEPRECATION")
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)

    }
}