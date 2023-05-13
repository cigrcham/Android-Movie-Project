package com.example.androidmoviesproject.data.firebase

import com.example.androidmoviesproject.data.model.ModelNotification
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

/** Get Data from Firebase */
class NotificationFirebase {
    fun getData(data: (List<ModelNotification>?) -> Unit = {}) {
        val database = Firebase.database.getReference("Notification")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listNotify = mutableListOf<ModelNotification>()
                val value = snapshot.children.iterator()
                while (value.hasNext()) {
                    val notify = value.next().getValue<ModelNotification>()
                    if (notify != null) {
                        listNotify.add(notify)
                    }
                }
                data.invoke(listNotify)
            }

            override fun onCancelled(error: DatabaseError) {
                data.invoke(null)
            }
        })
    }
}