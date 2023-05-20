package com.example.littlelemon

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString


class MainActivity : ComponentActivity() {
    private lateinit var httpClient: HttpClient
    private lateinit var database: AppDatabase

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "menu-db")
            .build()


        httpClient = HttpClient {
            install(ContentNegotiation) { json() }

        }
        GlobalScope.launch(Dispatchers.IO) {

            val response = kotlinx.serialization.json.Json.decodeFromString(
                httpClient
                    .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json") {
                        contentType(Json)
                    }
                    .body()
            ) as MenuNetworkData


            response.apply {
                menu.map {
                    val entity = MenuItemEntity(
                        id = it.id,
                        description = it.description,
                        image = it.image,
                        title = it.title,
                        price = it.price,
                        category = it.category
                    )
                    database.menuDao().insert(entity)

                }
            }

        }



        setContent {
            LittleLemonTheme {
                val navController = rememberNavController()
                MyNavigation(
                    navController = navController,
                    sharedPreferences,
                    database
                )

            }
        }
    }
}


