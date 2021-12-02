package dayN

import java.io.File

fun main() {
    println("Part one: ${DayN.solvePartOne()}")
    println("Part two: ${DayN.solvePartTwo()}")
}

object DayN {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private fun parseInput(): List<String> =
        File("src/main/kotlin/$directory/input.txt").readLines()

    fun solvePartOne(): Int {
        return -1
    }

    fun solvePartTwo(): Int {
        return -1
    }
}