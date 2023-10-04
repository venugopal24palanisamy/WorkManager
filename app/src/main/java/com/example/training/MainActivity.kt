package com.example.training

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.training.ui.theme.TrainingTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@SuppressLint("InvalidPeriodicWorkRequestInterval")
class MainActivity : ComponentActivity() {

    lateinit var workManager: WorkManager

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            //val mainViewModel = viewModels<MainViewModel>()


            //var counts = mainViewModel.value.count
            var s by remember { mutableStateOf("hi") }
            val context = LocalContext.current
            var state by remember { mutableStateOf(false) }

            /*workManager = WorkManager.getInstance(context)
            val powerConstraint =
                Constraints.Builder().setRequiredNetworkType(networkType = NetworkType.CONNECTED)
                    .setRequiresCharging(true).build()
            val request =
                OneTimeWorkRequest.Builder(MyWorkCls::class.java).setConstraints(powerConstraint)
                    .build()
            var state by remember { mutableStateOf(false) }
            var isPermissionGranted by remember { mutableStateOf(false) }
            val requestPermissionLauncher =
                rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { isGranted ->
                    isPermissionGranted = isGranted
                }
            */

            /*if (state) {
                mainViewModel.value.scheduleNotification(context)
            }*/


            /*Column {
                Text(text = mainViewModel.value._myLiveData.toString() ?: "Default Value")
                TextField(value = s, onValueChange = {
                    mainViewModel.value.updateValue(s)
                })
            }*/



            MyComposable()


            /*TrainingTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextField(value = mainViewModel.value.phoneNumber,
                                onValueChange = { mainViewModel.value.phoneNumber = it },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                placeholder = {
                                    Text(
                                        text = "Phone Number",
                                    )
                                }, supportingText = {
                                    if (mainViewModel.value.isPhoneNumberError) Text(
                                        text = mainViewModel.value.phoneNumberError, color = Color.Red
                                    )
                                })
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(15.dp)
                            )
                            TextField(value = mainViewModel.value.message,
                                onValueChange = { mainViewModel.value.message = it },
                                placeholder = {
                                    Text(
                                        text = "Message",
                                    )
                                }, supportingText = {
                                    if (mainViewModel.value.isMessageError) Text(
                                        text = mainViewModel.value.messageError, color = Color.Red
                                    )
                                })
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(15.dp)
                            )
                            Button(onClick = {
                                if (mainViewModel.value.validate()) state = !state
                            }) {
                                Text(
                                    text = "Push Message",
                                )
                            }

                            *//*MyButton(currentCount = counts) {
                            mainViewModel.value.increaseCount()
                        }*//*

                    }
                }
            }*/

            //LiveData()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveData() {

    //val mainViewModel = viewModels<MainViewModel>()
    //val myLiveDataValue by viewModel.myLiveData.observeAsState()


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyComposable() {
    var s by remember { mutableStateOf("hi") }
    val viewModel: ViewModel = viewModel()
    val myLiveDataValue by viewModel.myLiveData.observeAsState()

    Column {
        Text(text = myLiveDataValue ?: "Default Value")
        TextField(value = s, onValueChange = {
            viewModel.updateValue(s)
        })
    }
}

class CounterViewModel : ViewModel() {

    val _myLiveData = MutableLiveData<String>()
    val myLiveData: LiveData<String> get() = _myLiveData

    fun updateValue(newValue: String) {
        _myLiveData.value = newValue
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TrainingTheme {
        //Greeting("Android")
    }
}