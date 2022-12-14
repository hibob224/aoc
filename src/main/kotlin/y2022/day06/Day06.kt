package y2022.day06

import utils.getInputFile

fun main() {
    println("Part one: ${Day06.solvePartOne()}")
    println("Part two: ${Day06.solvePartTwo()}")
}

object Day06 {

    private val input = getInputFile(this::class.java.packageName).readText()

    fun solvePartOne(): Int = findMarker(4)

    fun solvePartTwo(): Int = findMarker(14)

    private fun findMarker(length: Int): Int {
        val marker = input
            .windowed(length)
            .first { it.length == it.toSet().size }
        return input.indexOf(marker) + marker.length
    }
}
