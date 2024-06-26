package com.example.milionar.DataManagement

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.milionar.NotificationHelper
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class ScoreManager(private val context: Context) {
    private val fileName = "scores.json"
    var highScore = 0
    fun saveScore(score: Score) {
        val scores = loadScores().toMutableList()
        scores.add(score)
        scores.sortByDescending { it.score }
        if (scores.size > 10) {
            scores.removeLast()
        }
        val json = Json.encodeToString(scores)
        File(context.filesDir, fileName).writeText(json)
    }

    fun createNotification(title: String, message: String) {
        val notificationHelper = NotificationHelper(context)
        notificationHelper.createNotification(title, message)
    }

    fun reloadHighScore() {
        var listt = loadScores()
        if (listt.isNotEmpty()) {
            highScore = listt[0].score
        }
    }

    fun loadScores(): List<Score> {
        val file = File(context.filesDir, fileName)
        return if (file.exists()) {
            val json = file.readText()
            Json.decodeFromString(json)
        } else {
            emptyList()
        }
    }

    fun resetScoreboard() {
        val scores = emptyList<Score>()
        val json = Json.encodeToString(scores)
        File(context.filesDir, fileName).writeText(json)
    }
}