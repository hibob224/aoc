package y2020.day06

import java.io.File

fun main() {
    println("Part one: ${Day06.solvePartOne()}")
    println("Part two: ${Day06.solvePartTwo()}")
}

object Day06 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val input: List<String> =
        File("src/main/kotlin/$directory/input.txt")
            .readText()
            .split("\n\n")

    fun solvePartOne(): String {
        return input
            .map { group ->
                group.replace("\n", "")
                    .toSet()
                    .size
            }
            .sum()
            .toString()
    }

    fun solvePartTwo(): String {
        return input
            .map { group ->
                val groupSize = group.count { it == '\n' }.inc() // Number of line breaks + 1 == number of people
                group.groupBy { it }
                    .filter { it.value.size == groupSize }
                    .size
            }
            .sum()
            .toString()
    }
}