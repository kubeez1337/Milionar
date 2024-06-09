package com.example.milionar

data class Otazky(val id: Int,
                  val question: String,
                  val options: List<String>,
                  val correctAnswer: Int,
                  val category: String,
                  val difficulty: Difficulty,
                  val theme: String)
enum class Difficulty {
    Lahka, Stredna, Tazka
}
fun generateQuestions() : List<Otazky> {
    val question6 = Otazky(
        id = 6,
        question = "What is the capital of France?",
        options = listOf("Berlin", "Madrid", "Paris", "Rome"),
        correctAnswer = 2,
        category = "Geography",
        difficulty = Difficulty.Tazka,
        theme = "Capitals"
    )
    val question7 = Otazky(
        id = 7,
        question = "Which planet is known as the Red Planet?",
        options = listOf("Earth", "Mars", "Jupiter", "Saturn"),
        correctAnswer = 1,
        category = "Astronomy",
        difficulty = Difficulty.Stredna,
        theme = "Planets"
    )
    val question1 = Otazky(
        id = 1,
        question = "Aká je najväčšia planéta v slnečnej sústave?",
        options = listOf("Zem", "Mars", "Jupiter", "Saturn"),
        correctAnswer = 2,
        category = "Astronómia",
        difficulty = Difficulty.Lahka,
        theme = "Planéty"
    )

    val question2 = Otazky(
        id = 2,
        question = "Koľko je 10 - 4?",
        options = listOf("5", "6", "7", "8"),
        correctAnswer = 1,
        category = "Matematika",
        difficulty = Difficulty.Lahka,
        theme = "Základné operácie"
    )

    val question3 = Otazky(
        id = 3,
        question = "Ktorý prvok má chemický symbol O?",
        options = listOf("Vápnik", "Kyslík", "Zlato", "Striebro"),
        correctAnswer = 1,
        category = "Chémia",
        difficulty = Difficulty.Stredna,
        theme = "Chemické prvky"
    )

    val question4 = Otazky(
        id = 4,
        question = "Kto bol prvým prezidentom Česko-Slovenska?",
        options = listOf("Tomáš Garrigue Masaryk", "Edvard Beneš", "Alexander Dubček", "Gustáv Husák"),
        correctAnswer = 0,
        category = "História",
        difficulty = Difficulty.Stredna,
        theme = "Politické osobnosti"
    )

    val question5 = Otazky(
        id = 5,
        question = "Kto napísal dielo 'Mor ho!'?",
        options = listOf("Pavol Országh Hviezdoslav", "Janko Kráľ", "Samo Chalupka", "Ľudovít Štúr"),
        correctAnswer = 2,
        category = "Literatúra",
        difficulty = Difficulty.Tazka,
        theme = "Slovenská literatúra"
    )
    return listOf(question1, question2)
}

