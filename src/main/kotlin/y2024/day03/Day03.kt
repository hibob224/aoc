package y2024.day03

import utils.getInputFile

fun main() {
    println("Part one: ${Day03.solvePartOne()}")
    println("Part two: ${Day03.solvePartTwo()}")
}

object Day03 {

    private val input = getInputFile().readText()

    fun solvePartOne(): Int {
        return input
            .let { """mul\((\d{1,3}),(\d{1,3})\)""".toRegex().findAll(it) }
            .map { it.groupValues[1].toInt() to it.groupValues[2].toInt() }
            .sumOf { it.first * it.second }
    }

    fun solvePartTwo(): Int {
        return input
            .let { """do(?:n't)?\(\)|mul\((\d{1,3}),(\d{1,3})\)""".toRegex().findAll(it) }
            .fold(true to 0) { acc, match ->
                val (_, param1, param2) = match.groupValues
                when (match.value) {
                    "do()" -> true to acc.second
                    "don't()" -> false to acc.second
                    else -> if (acc.first) {
                        true to acc.second + (param1.toInt() * param2.toInt())
                    } else {
                        acc
                    }
                }
            }.second
    }
}
