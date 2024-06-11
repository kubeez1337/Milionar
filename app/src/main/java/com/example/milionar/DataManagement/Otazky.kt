package com.example.milionar.DataManagement

import kotlinx.serialization.Serializable

@Serializable
data class Otazky(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswer: Int,
    val category: String,
    val difficulty: String
)
