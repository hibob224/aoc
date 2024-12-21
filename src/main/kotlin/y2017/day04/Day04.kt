package y2017.day04

import utils.getInputFile

fun main() {
    println("Part one: " + Day04.solvePartOne())
    println("Part two: " + Day04.solvePartTwo())
}

object Day04 {

    private val input = getInputFile().readLines()

    fun solvePartOne(): Int =
        input
            .map { it.split(" ") }
            .count { it.size == it.distinct().size }

    fun solvePartTwo(): Int =
        input
            .map { it.split(" ").map { it.toCharArray().sorted().toCharArray().joinToString() } }
            .count { it.size == it.distinct().size }
}
