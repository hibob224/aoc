package y2022.day01

import utils.getInputFile
import utils.groupOnNulls

fun main() {
    println("Part one: ${Day01.solvePartOne()}")
    println("Part two: ${Day01.solvePartTwo()}")
}

object Day01 {

    private val input =
        getInputFile()
            .readLines()
            .map(String::toIntOrNull)
            .groupOnNulls()
            .map(List<Int>::sum)

    fun solvePartOne(): Int = input.maxOrNull()!!

    fun solvePartTwo(): Int = input.sortedDescending().take(3).sum()
}
