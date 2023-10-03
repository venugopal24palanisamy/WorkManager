package com.example.training

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.training.ui.theme.TrainingTheme
import java.util.concurrent.TimeUnit
import android.Manifest
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.log

@SuppressLint("InvalidPeriodicWorkRequestInterval")
class MainActivity : ComponentActivity() {

    lateinit var workManager: WorkManager
    lateinit var smsManager: SmsManager

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val mainViewModel = viewModels<MainViewModel>()
            var counts = mainViewModel.value.count
            val context = LocalContext.current
            workManager = WorkManager.getInstance(context)
            val powerConstraint =
                Constraints.Builder().setRequiredNetworkType(networkType = NetworkType.CONNECTED)
                    .setRequiresCharging(true).build()
            val request =
                OneTimeWorkRequest.Builder(MyWorkCls::class.java).setConstraints(powerConstraint)
                    .build()

            val periodicUpdate =
                PeriodicWorkRequest.Builder(MyWorkCls::class.java, 5, TimeUnit.SECONDS)
                    .build()

            var state by remember { mutableStateOf(false) }
            var phoneNumber by remember { mutableStateOf("") }
            var message by remember { mutableStateOf("") }
            var status by remember { mutableStateOf(false) }
            var isPermissionGranted by remember { mutableStateOf(false) }

            val requestPermissionLauncher =
                rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { isGranted ->
                    isPermissionGranted = isGranted
                }

           if (state){
               val workManager = mainViewModel.value.scheduleNotification(context)

               workManager.getWorkInfoByIdLiveData(periodicUpdate.id)
                   .observe(this) {
                       println("========== Work status: $it.status  \n")
                       //  stats= "\n"+it.state.name+"\n"
                       Log.d("onCreate", "$it.status")
                   }
           }
            /*if (state) {
                workManager.enqueue(periodicUpdate)
                workManager.getWorkInfoByIdLiveData(periodicUpdate.id)
                    .observe(this) {
                        println("========== Work status: $it.status  \n")
                        //  stats= "\n"+it.state.name+"\n"
                        Log.d("onCreate", "$it.status")
                    }
                if (isPermissionGranted) {
                    //sendSms(phoneNumber, message)
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
                }
            }*/
            TrainingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextField(value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            placeholder = {
                                Text(
                                    text = "Phone Number",
                                )
                            })
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(15.dp)
                        )
                        TextField(value = message,
                            onValueChange = { message = it },
                            placeholder = {
                                Text(
                                    text = "Message",
                                )
                            })
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(15.dp)
                        )
                        Button(onClick = {
                            state = !state
                        }) {
                            Text(
                                text = "Push Message",
                            )
                        }

                        /*MyButton(currentCount = counts) {
                            mainViewModel.value.increaseCount()
                        }*/

                    }
                }
            }
        }
    }
}

@Composable
fun MyButton(currentCount: Int, updateCount: () -> Unit) {
    Button(onClick = {
        updateCount()
    }) {
        Text(
            text = "Increase Count $currentCount",
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {


}


fun notifexpl(title: String, description: String, context: Context)
        : Boolean {

    var notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val notificationChannel =
        NotificationChannel("101", "channel", NotificationManager.IMPORTANCE_DEFAULT)
    notificationManager.createNotificationChannel(notificationChannel)

    var inte = Intent(context, MainActivity::class.java)

    var pendin = PendingIntent.getActivity(
        context,
        0,
        inte,
        PendingIntent.FLAG_IMMUTABLE
    )
    val notificationBuilder = NotificationCompat.Builder(context, "101")
        .setContentTitle(title)
        .setContentText(description)
        .setContentIntent(pendin)
        .setSmallIcon(android.R.drawable.star_big_on)

    notificationManager.notify(1, notificationBuilder.build())

    return true

    //Notification:
    // Intent .
    // pendingIntent.
    // Notification.builder.
    // NotificationManager.
    // Notification Channel
    // version check

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TrainingTheme {
        Greeting("Android")
    }
}