package com.example.milionar

import kotlinx.serialization.Serializable

@Serializable
data class Score(val name: String, val score: Int, val date: String, val theme: String)


