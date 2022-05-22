package com.example.integralmed.ui.activities

import android.app.Dialog
import android.view.LayoutInflater
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

        var view = LayoutInflater.from(this).inflate(R.layout.dialog_progress, null)
        var text_view_progress_text = view.findViewById<TextView>(R.id.text_view_progress_text)
        myShowProgressDialog = Dialog(this)
        myShowProgressDialog.setContentView(R.layout.dialog_progress)
        myShowProgressDialog.setCancelable(false)
        myShowProgressDialog.setCanceledOnTouchOutside(false)
        text_view_progress_text.text = text
        myShowProgressDialog.show()
    }


    fun hideProgressDialog() {
        myShowProgressDialog.dismiss()
    }

}