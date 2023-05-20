package com.example.littlelemon.pages.onboarding

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R
import com.example.littlelemon.User
import com.example.littlelemon.ui.WarningDialog
import com.google.gson.Gson

@Composable
fun OnBoardingScreen(
    sharedPreferences: SharedPreferences?,
    onRegister: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()

        RegisterForm(sharedPreferences, onRegister)
    }
}

@Preview
@Composable
fun PreviewRegisterScreen() {
    OnBoardingScreen(null) {}
}

@Composable
fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Logo",
        modifier = Modifier
            .size(150.dp)
            .padding(bottom = 16.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterForm(
    sharedPreferences: SharedPreferences?,
    onRegister: () -> Unit
) {
    var showDialogError by remember { mutableStateOf(false) }


    WarningDialog(showDialog = showDialogError) {
        showDialogError = false
    }

    Text(
        text = stringResource(id = R.string.let_s_get_to_know_you),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(bottom = 32.dp)
    )

    val fNameState = remember { mutableStateOf(TextFieldValue()) }
    val lNameState = remember { mutableStateOf(TextFieldValue()) }
    val emailState = remember { mutableStateOf(TextFieldValue()) }

    TextField(
        value = fNameState.value,
        onValueChange = { fNameState.value = it },
        label = { Text(stringResource(id = R.string.first_name)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    )

    TextField(
        value = lNameState.value,
        onValueChange = { lNameState.value = it },
        label = { Text(stringResource(id = R.string.last_name)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    )

    TextField(
        value = emailState.value,
        onValueChange = { emailState.value = it },
        label = { Text(stringResource(id = R.string.email)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
    )

    Button(
        onClick = {
            validateForm(
                fName = fNameState.value.text,
                lName = lNameState.value.text,
                email = emailState.value.text,
            ).apply {
                when (this) {
                    true -> {
                        val gson = Gson()
                        val userDataJson = gson.toJson(
                            User(
                                firstName = fNameState.value.text,
                                lastName = lNameState.value.text,
                                email = emailState.value.text
                            )

                        )
                        sharedPreferences?.edit()?.putString("User", userDataJson)?.apply()
                        onRegister()
                    }

                    false -> {
                        showDialogError = true
                    }
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.register))
    }
}


fun validateForm(fName: String, lName: String, email: String): Boolean {
    val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")

    return if (fName.isBlank()) {
        false
    } else if (lName.isBlank()) {
        false
    } else email.matches(emailRegex)
}
