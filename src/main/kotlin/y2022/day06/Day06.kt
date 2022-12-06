package y2022.day06

import java.io.File

fun main() {
    println("Part one: ${Day06.solvePartOne()}")
    println("Part two: ${Day06.solvePartTwo()}")
}

object Day06 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val input = File("src/main/kotlin/$directory/input.txt").readText()

    fun solvePartOne(): Int = findMarker(4)

    fun solvePartTwo(): Int = findMarker(14)

    private fun findMarker(length: Int): Int {
        val marker = input
            .windowed(length)
            .first { it.groupBy { it }.size == length }
        return input.indexOf(marker) + marker.length
    }
}
