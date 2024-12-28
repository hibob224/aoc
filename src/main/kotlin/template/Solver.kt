package template

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import networking.client
import utils.print
import java.io.File
import kotlin.time.measureTimedValue

fun solve(puzzleProvider: () -> Puzzle<*, *>) {
    val puzzle = puzzleProvider()
    val (p1, p1d) = measureTimedValue { puzzle.solvePartOne() }
    val (p2, p2d) = measureTimedValue { puzzle.solvePartTwo() }
    println("Solving Y${puzzle.year}-D${puzzle.day}")
    println("Part One: ${p1!!.bold()} - ${p1d.inWholeMilliseconds} ms")
    submitAnswer(p1.toString(), puzzle.year, puzzle.day, 1, puzzle.example)
    println("Part Two: ${p2!!.bold()} - ${p2d.inWholeMilliseconds} ms")
    submitAnswer(p2.toString(), puzzle.year, puzzle.day, 2, puzzle.example)
}

private fun submitAnswer(answer: String, year: Int, day: Int, part: Int, example: Boolean) {
    if (answer.isBlank() || answer == "0") return
    if (isSolved(year, day, part) || example) return
    print("Submit answer? (Yes/No/Ignore): ")
    val inp = readln().lowercase()
    when (inp) {
        "y" -> submitAnswer(answer, year, day, part)
        "i" -> markSubmitted(year, day, part)
    }
}

private fun submitAnswer(answer: String, year: Int, day: Int, part: Int) = runBlocking {
    val response = client.post("https://adventofcode.com/$year/day/$day/answer") {
        setBody(parameters {
            append("level", part.toString())
            append("answer", answer)
        }.formUrlEncode())
        contentType(ContentType.Application.FormUrlEncoded)
    }

    val result = """article>(.*)</article""".toRegex()
        .find(response.bodyAsText())!!
        .groupValues[1]
        .replace("""<a href.*?</a>""".toRegex(), "")
        .replace("<p>", "")
        .replace("</p>", "")
        .print()

    if ("That's the right answer!" in result) {
        markSubmitted(year, day, part)
    }
}

private fun isSolved(year: Int, day: Int, part: Int): Boolean {
    val key = "${year}.${day}.${part}"
    val submission = File("submission.cache").readLines()
    return key in submission
}

private fun markSubmitted(year: Int, day: Int, part: Int) {
    File("submission.cache").appendText("${year}.${day}.$part\n")
}

private fun Any.bold(): String = "\u001B[33;1m$this\u001B[0m"
