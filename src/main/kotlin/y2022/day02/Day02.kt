package y2022.day02

import utils.getInputFile

fun main() {
    println("Part one: ${Day02.solvePartOne()}")
    println("Part two: ${Day02.solvePartTwo()}")
}

object Day02 {

    private val input =
        getInputFile()
            .readLines()
            .map {
                val split = it.split(" ", limit = 2)
                split[0] to split[1]
            }

    fun solvePartOne(): Int {
        val resultMap = mapOf(
            ("A" to "X") to DRAW,
            ("A" to "Y") to WIN,
            ("A" to "Z") to LOSS,
            ("B" to "X") to LOSS,
            ("B" to "Y") to DRAW,
            ("B" to "Z") to WIN,
            ("C" to "X") to WIN,
            ("C" to "Y") to LOSS,
            ("C" to "Z") to DRAW,
        )
        val playMap = mapOf(
            "X" to ROCK,
            "Y" to PAPER,
            "Z" to SCISSORS,
        )
        return input.sumOf { resultMap[it]!! + playMap[it.second]!! }
    }

    fun solvePartTwo(): Int {
        val playMap = mapOf(
            ("A" to "X") to SCISSORS,
            ("A" to "Y") to ROCK,
            ("A" to "Z") to PAPER,
            ("B" to "X") to ROCK,
            ("B" to "Y") to PAPER,
            ("B" to "Z") to SCISSORS,
            ("C" to "X") to PAPER,
            ("C" to "Y") to SCISSORS,
            ("C" to "Z") to ROCK,
        )
        val resultMap = mapOf(
            "X" to LOSS,
            "Y" to DRAW,
            "Z" to WIN,
        )
        return input.sumOf { resultMap[it.second]!! + playMap[it]!! }
    }

    private const val WIN = 6
    private const val DRAW = 3
    private const val LOSS = 0
    private const val ROCK = 1
    private const val PAPER = 2
    private const val SCISSORS = 3
}
