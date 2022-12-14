package y2021.day01

import utils.getInputFile

fun main() {
    println("Part one: ${Day01.solvePartOne()}")
    println("Part two: ${Day01.solvePartTwo()}")
}

object Day01 {

    private val input =
        getInputFile(this::class.java.packageName)
            .readLines()
            .map { it.toInt() }

    fun solvePartOne(): Int = input
        .windowed(2)
        .count { it[1] > it[0] }

    fun solvePartTwo(): Int = input.windowed(3)
        .map(List<Int>::sum)
        .windowed(2)
        .count { it[1] > it[0] }
}