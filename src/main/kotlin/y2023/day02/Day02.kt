package y2023.day02

import utils.getInputFile

fun main() {
    println("Part one: ${Day02.solvePartOne()}")
    println("Part two: ${Day02.solvePartTwo()}")
}

// Original solution written on Kotlin Playground, on mobile...
object Day02 {

    private val regex = """(\d+) (red|green|blue)""".toRegex()
    private val input =
        getInputFile(this::class.java.packageName)
            .readLines()
            .map { it.split(": ")[1].split("; ") }
            .map { it.flatMap { regex.findAll(it).toList() } }
            .map {
                it.map {
                    val (value, colour) = it.destructured
                    colour to value.toInt()
                }
            }

    fun solvePartOne(): Int {
        var sum = 0

        input.onEachIndexed { index, pulls ->
            val valid = pulls.all { (colour, value) ->
                (colour == "red" && value <= 12) ||
                    (colour == "green" && value <= 13) ||
                    (colour == "blue" && value <= 14)
            }

            if (valid) {
                sum += index.inc()
            }
        }

        return sum
    }

    fun solvePartTwo(): Long {
        return input
            .map { it.groupBy { it.first } } // Group each line by colours
            .map { it.map { it.value.map { it.second }.max() } } // Find the largest value for each colour in each line
            .sumOf {
                it.fold(1L) { acc, t -> acc * t }
            }
    }
}
