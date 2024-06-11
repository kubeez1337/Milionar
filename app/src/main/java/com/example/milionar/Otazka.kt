package com.example.milionar

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File


class Otazka(private val context: Context){
    private val fileName = "otazky.json"
    fun validateFilter(zoznam: List<Otazky>, theme: String, difficulty: String) : List<Otazky> {
        var res = mutableListOf<Otazky>()
        for (i in zoznam) {
            if (theme == "") {
                if (difficulty == "") {
                    res.add(i)
                } else if ((i.difficulty) == difficulty) {
                    res.add(i)
                }
            } else if (i.category == theme) {
                if (difficulty == "") {
                    res.add(i)
                } else if ((i.difficulty) == difficulty) {
                    res.add(i)
                }
            }
        }
        return res
    }
    fun generateQuestions(theme: String, difficulty: String) : List<Otazky> {
        val otazk = loadQuestions()
        return validateFilter(otazk, theme, difficulty)
    }
    fun loadQuestions(): List<Otazky> {
        return try {
            val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            Json.decodeFromString(jsonString)
        } catch (e: Exception) {
            println("Error loading questions: ${e.message}")
            emptyList()
        }
    }
}




