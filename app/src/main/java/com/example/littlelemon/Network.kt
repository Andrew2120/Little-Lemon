package com.example.littlelemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MenuNetworkData(
    @SerialName("menu")
    val menu: List<Menu>
) {
    @Serializable
    data class Menu(
        @SerialName("category")
        val category: String,
        @SerialName("description")
        val description: String,
        @SerialName("id")
        val id: Int,
        @SerialName("image")
        val image: String,
        @SerialName("price")
        val price: String,
        @SerialName("title")
        val title: String
    )
}