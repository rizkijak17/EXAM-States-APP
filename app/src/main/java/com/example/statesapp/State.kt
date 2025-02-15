package com.example.statesapp

data class State(
    val name: String,
    val population: Int? = 0,  // Menangani nilai null, defaultkan ke 0
    val imageUrl: String? = ""  // Menangani nilai null, defaultkan ke string kosong
)
