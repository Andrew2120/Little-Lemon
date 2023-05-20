package com.example.littlelemon

interface Destinations {
    val onboarding: String
    val home: String
    val profile: String
}

object MyDestinations : Destinations {
    override val onboarding: String = "onboarding"
    override val home: String = "home"
    override val profile: String = "profile"
}
