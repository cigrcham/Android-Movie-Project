package com.example.androidmoviesproject.data.firebase

import com.example.androidmoviesproject.data.model.ModelNotification

interface FirebaseNotification {
    fun getNotificationData(data: (List<ModelNotification>?) -> Unit = {})
}