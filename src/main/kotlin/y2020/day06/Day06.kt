package y2020.day06

import utils.getInputFile

fun main() {
    println("Part one: ${Day06.solvePartOne()}")
    println("Part two: ${Day06.solvePartTwo()}")
}

object Day06 {

    private val input: List<String> =
        getInputFile(this::class.java.packageName)
            .readText()
            .split("\n\n")

    fun solvePartOne(): String = input.sumOf { group ->
        group.replace("\n", "")
            .toSet()
            .size
    }.toString()

    fun solvePartTwo(): String = input.sumOf { group ->
        val groupSize = group.count { it == '\n' }.inc() // Number of line breaks + 1 == number of people
        group.groupBy { it }
            .filter { it.value.size == groupSize }
            .size
    }.toString()
}