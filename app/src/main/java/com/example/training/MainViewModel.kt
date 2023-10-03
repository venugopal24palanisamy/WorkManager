package com.example.training

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {

    var count by mutableStateOf(0)

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
        Log.d("scheduleNotification","insideViewModel")
        return workManager
    }
}