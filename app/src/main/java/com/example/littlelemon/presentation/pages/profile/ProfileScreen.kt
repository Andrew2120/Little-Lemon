package com.example.littlelemon.presentation.pages.profile

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R
import com.example.littlelemon.domain.entities.User
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
            .size(100.dp)
            .padding(bottom = 16.dp)
    )
}

@Composable
fun UserData(
    user: User,
    onLogout: () -> Unit
) {

    Text(
        text = stringResource(id = R.string.personal_information),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(bottom = 32.dp)
    )


    Text(
        user.firstName,
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
    Text(
        user.lastName,
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
    Text(
        user.email,
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )


    Button(
        onClick = {
            onLogout()
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.logout))
    }
}


