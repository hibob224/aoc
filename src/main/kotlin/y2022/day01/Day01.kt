package y2022.day01

import utils.groupOnNulls
import java.io.File

fun main() {
    println("Part one: ${Day01.solvePartOne()}")
    println("Part two: ${Day01.solvePartTwo()}")
}

object Day01 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val input =
        File("src/main/kotlin/$directory/input.txt")
            .readLines()
            .map(String::toIntOrNull)
            .groupOnNulls()
            .map(List<Int>::sum)

    fun solvePartOne(): Int = input.maxOrNull()!!

    fun solvePartTwo(): Int = input.sortedDescending().take(3).sum()
}
