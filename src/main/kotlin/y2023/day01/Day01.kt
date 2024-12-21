package y2023.day01

import utils.getInputFile
import utils.replaceAll

fun main() {
    println("Part one: ${Day01.solvePartOne()}")
    println("Part two: ${Day01.solvePartTwo()}")
}

// Original solution written on Kotlin Playground, on mobile...
object Day01 {

    private val input = getInputFile().readLines()

    fun solvePartOne(): Int =
        input
            .map { "${it.first { it.isDigit() }}${it.last { it.isDigit() }}" }
            .sumOf { it.toInt() }

    fun solvePartTwo(): Int {
        val replacements = listOf(
            "twone" to "2one",
            "oneight" to "1eight",
            "threeight" to "3eight",
            "fiveight" to "5eight",
            "nineight" to "9eight",
            "eightwo" to "8two",
            "eighthree" to "8three",
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9",
        )
        return input
            .map { it.replaceAll(replacements) }
            .map { "${it.first { it.isDigit() }}${it.last { it.isDigit() }}" }
            .sumOf { it.toInt() }
    }
}
