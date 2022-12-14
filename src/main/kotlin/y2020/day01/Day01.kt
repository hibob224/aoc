package y2020.day01

import utils.getInputFile

fun main() {
    println("Part one: ${Day01.solvePartOne()}")
    println("Part two: ${Day01.solvePartTwo()}")
}

object Day01 {

    private fun parseInput(): List<Int> =
        getInputFile(this::class.java.packageName)
            .readLines()
            .map(String::toInt)

    fun solvePartOne(): String {
        val input = parseInput()

        return input
            .filter { first -> input.any { second -> first + second == 2020 } }
            .fold(initial = 1) { acc, i -> acc * i }
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