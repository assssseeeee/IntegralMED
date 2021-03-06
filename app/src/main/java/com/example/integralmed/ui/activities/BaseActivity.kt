package com.example.integralmed.ui.activities

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.integralmed.R
import com.google.android.material.snackbar.Snackbar


open class BaseActivity : AppCompatActivity() {
    private lateinit var myShowProgressDialog: Dialog

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

}