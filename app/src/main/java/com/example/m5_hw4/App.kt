package com.example.m5_hw4

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
//Все приложения использующие Hilt должны содержать класс Application c анатацией @HiltAndroidApp
}