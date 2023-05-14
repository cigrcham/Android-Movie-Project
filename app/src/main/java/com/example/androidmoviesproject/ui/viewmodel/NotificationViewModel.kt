package com.example.androidmoviesproject.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmoviesproject.data.firebase.NotificationFirebase
import com.example.androidmoviesproject.data.model.ModelNotification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor() : ViewModel() {
    init {
        getNotification()
    }

    private val _notifyData: MutableLiveData<List<ModelNotification>> = MutableLiveData()
    fun notifyData() = _notifyData
    private fun getNotification() {
        viewModelScope.launch(Dispatchers.IO) {
            val notifyFirebase = NotificationFirebase()
            notifyFirebase.getData {
                if (it != null) {
                    _notifyData.postValue(it)
                }
            }
        }
    }
}