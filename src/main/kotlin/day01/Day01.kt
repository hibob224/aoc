package day01

import java.io.File

fun main() {
    println("Part one: ${Day01.solvePartOne()}")
    println("Part two: ${Day01.solvePartTwo()}")
}

object Day01 {

    private val directory: String
        get() = this::class.java.`package`.name

    private fun parseInput(): List<Int> =
        File("src/main/kotlin/$directory/input.txt")
            .readLines()
            .map(String::toInt)

    fun solvePartOne(): String {
        val input = parseInput()

        return input
            .filter { first -> input.any { second -> first + second == 2020 } }
            .fold(initial = 1) { acc , i -> acc * i }
            .toString()
    }

    fun solvePartTwo(): String {
        val input = parseInput()

        return input
            .filter { first ->
                input.any { second ->
                    input.any { third ->
                        first + second + third == 2020
                    }
                }
            }
            .fold(1) { acc, i -> acc * i }
            .toString()
    }
}