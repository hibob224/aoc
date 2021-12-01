package y2021.day01

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
            .map { it.toInt() }

    fun solvePartOne(): Int = input
        .windowed(2)
        .count { it[1] > it[0] }

    fun solvePartTwo(): Int = input.windowed(3)
        .map(List<Int>::sum)
        .windowed(2)
        .count { it[1] > it[0] }
}