package com.example.integralmed.firestore

import android.util.Log
import android.widget.Toast
import com.example.integralmed.models.User
import com.example.integralmed.ui.activities.RegisterActivity
import com.example.integralmed.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {
    private val myFirestore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User) {
        myFirestore.collection(Constants.USERS).document(userInfo.id)
            .set(userInfo, SetOptions.merge()).addOnSuccessListener {
                Toast.makeText(activity.baseContext, "User add complete", Toast.LENGTH_SHORT)
            }.addOnFailureListener { e ->
                Log.e(activity.javaClass.simpleName, "Error while register with user")
            }
    }
}