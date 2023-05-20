package com.example.littlelemon.pages.profile

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R
import com.example.littlelemon.User
import com.google.gson.Gson

@Composable
fun ProfileScreen(
    sharedPreferences: SharedPreferences,
    onLogout: () -> Unit
) {
    val gson = Gson()
    val userDataJson = sharedPreferences.getString("User", null)
    val user = gson.fromJson(userDataJson, User::class.java)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        UserData(user, onLogout)
    }
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

@Composable
fun UserData(
    user: User?,
    onLogout: () -> Unit
) {

    Column(Modifier.padding(16.dp)) {
        DataFiled(stringResource(id = R.string.first_name), user?.firstName ?: "")
        DataFiled(stringResource(id = R.string.last_name), user?.lastName ?: "")
        DataFiled(stringResource(id = R.string.email), user?.email ?: "")


        Button(
            onClick = {
                onLogout()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.logout))
        }
    }
}


@Composable
fun DataFiled(key: String, value: String) {
    Text(
        key,
        Modifier
            .padding(4.dp)
            .fillMaxWidth()
    )
    Text(
        value,
        Modifier
            .padding(4.dp)
            .fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
    Divider()
    Spacer(modifier = Modifier.height(8.dp))
}