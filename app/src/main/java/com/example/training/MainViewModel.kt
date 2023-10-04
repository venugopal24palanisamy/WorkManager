package com.example.training

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {

    var count by mutableStateOf(0)
    var phoneNumber by mutableStateOf("")
    var message by mutableStateOf("")

    var isMessageError by mutableStateOf(false)
    var isPhoneNumberError by mutableStateOf(false)
    var messageError by mutableStateOf("")
    var phoneNumberError by mutableStateOf("")


    val _myLiveData = MutableLiveData<String>()
    val myLiveData: LiveData<String> get() = _myLiveData



    fun updateValue(newValue: String) {
        _myLiveData.value = newValue
    }

    fun increaseCount() {
        count++
    }


    @SuppressLint("InvalidPeriodicWorkRequestInterval")
    fun scheduleNotification(context: Context): WorkManager {
        val workManager = WorkManager.getInstance(context)
        val periodicUpdate = PeriodicWorkRequest.Builder(
            MyWorkCls::class.java, 1, TimeUnit.HOURS
        ).build()
        workManager.enqueue(periodicUpdate)
        Log.d("scheduleNotification", "insideViewModel")
        return workManager
    }

    fun validate(): Boolean {
        if (phoneNumber.isEmpty()) {
            isPhoneNumberError = true
            phoneNumberError = "Enter Phone Number"
            return false
        } else {
            phoneNumberError = ""
            isPhoneNumberError = false
        }

        if (phoneNumber.length > 10 || phoneNumber.length < 10) {
            isPhoneNumberError =true
            phoneNumberError = "Enter Valid Phone Number"
            return false
        } else {
            phoneNumberError = ""
            isPhoneNumberError = false
        }

        if (message.isEmpty()) {
            messageError = "Enter Message"
            isMessageError = true
            return false
        } else {
            messageError = ""
            isMessageError = true
        }

        return true
    }
}