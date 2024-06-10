package com.example.milionar


data class Otazky(val id: Int,
                  val question: String,
                  val options: List<String>,
                  val correctAnswer: Int,
                  val category: String,
                  val difficulty: Difficulty)
enum class Difficulty {
    Lahka, Stredna, Tazka
}
fun getStringFromDifficulty(difficulty: Difficulty): String {
    if (difficulty == Difficulty.Lahka) {
        return "Lahka"
    }
    if (difficulty == Difficulty.Stredna) {
        return "Stredna"
    }
    if (difficulty == Difficulty.Tazka) {
        return "Tazka"
    }
    return ""
}
fun validateFilter(zoznam: List<Otazky>, theme: String, difficulty: String) : List<Otazky> {
    var res = mutableListOf<Otazky>()
    for (i in zoznam) {
            if (theme == "") {
                if (difficulty == "") {
                    res.add(i)
                } else if (getStringFromDifficulty(i.difficulty) == difficulty) {
                    res.add(i)
                }
            } else if (i.category == theme) {
                if (difficulty == "") {
                    res.add(i)
                } else if (getStringFromDifficulty(i.difficulty) == difficulty) {
                    res.add(i)
                }
            }
    }
    return res
}
fun generateQuestions(theme: String, difficulty: String) : List<Otazky> {
    val question6 = Otazky(
        id = 6,
        question = "What is the capital of France?",
        options = listOf("Berlin", "Madrid", "Paris", "Rome"),
        correctAnswer = 2,
        category = "Geography",
        difficulty = Difficulty.Tazka,
    )
    val question7 = Otazky(
        id = 7,
        question = "Which planet is known as the Red Planet?",
        options = listOf("Earth", "Mars", "Jupiter", "Saturn"),
        correctAnswer = 1,
        category = "Astronomy",
        difficulty = Difficulty.Stredna
    )
    val question1 = Otazky(
        id = 1,
        question = "Aká je najväčšia planéta v slnečnej sústave?",
        options = listOf("Zem", "Mars", "Jupiter", "Saturn"),
        correctAnswer = 2,
        category = "Astronómia",
        difficulty = Difficulty.Lahka
    )

    val question2 = Otazky(
        id = 2,
        question = "Koľko je 10 - 4?",
        options = listOf("5", "6", "7", "8"),
        correctAnswer = 1,
        category = "Matematika",
        difficulty = Difficulty.Lahka
    )

    val question3 = Otazky(
        id = 3,
        question = "Ktorý prvok má chemický symbol O?",
        options = listOf("Vápnik", "Kyslík", "Zlato", "Striebro"),
        correctAnswer = 1,
        category = "Matematika",
        difficulty = Difficulty.Lahka,
    )
    val question4 = Otazky(
        id = 4,
        question = "Kto bol prvým prezidentom Česko-Slovenska?",
        options = listOf("Tomáš Garrigue Masaryk", "Edvard Beneš", "Alexander Dubček", "Gustáv Husák"),
        correctAnswer = 0,
        category = "História",
        difficulty = Difficulty.Stredna
    )
    val question5 = Otazky(
        id = 5,
        question = "Kto napísal dielo 'Mor ho!'?",
        options = listOf("Pavol Országh Hviezdoslav", "Janko Kráľ", "Samo Chalupka", "Ľudovít Štúr"),
        correctAnswer = 2,
        category = "Literatúra",
        difficulty = Difficulty.Tazka
    )
    val otazke = listOf(question1, question2,question3,question4,question5,question6,question7)

    return validateFilter(otazke, theme, difficulty)
    //return otazke.filter { it.category == theme && getStringFromDifficulty(it.difficulty) == difficulty }
}


