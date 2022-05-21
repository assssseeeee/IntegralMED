package com.example.integralmed.firestore

import android.util.Log
import com.example.integralmed.models.User
import com.example.integralmed.ui.activities.LoginActivity
import com.example.integralmed.ui.activities.RegisterActivity
import com.example.integralmed.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreClass {
    private val myFirestore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User) {
        myFirestore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistrationSuccess()
            }.addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Error while register with user", e)
            }
    }

    fun getUserDetails(loginActivity: LoginActivity) {
        TODO("Not yet implemented")
    }
}