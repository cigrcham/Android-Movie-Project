package com.example.androidmoviesproject.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmoviesproject.data.firebase.FirebaseNotification
import com.example.androidmoviesproject.data.model.ModelNotification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val firebaseNotify: FirebaseNotification) : ViewModel() {
    init {
        getNotification()
    }

    private val _notifyData: MutableLiveData<List<ModelNotification>> = MutableLiveData()
    fun notifyData() = _notifyData
    private fun getNotification() {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseNotify.getNotificationData {
                if (it != null) {
                    _notifyData.postValue(it)
                }
            }
        }
    }
}