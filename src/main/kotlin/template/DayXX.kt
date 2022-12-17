package template

import utils.getInputFile

fun main() {
    println("Part one: ${DayXX.solvePartOne()}")
    println("Part two: ${DayXX.solvePartTwo()}")
}

object DayXX {

    private val input = getInputFile(this::class.java.packageName).readLines()

    fun solvePartOne(): Long = 0

    fun solvePartTwo(): Long = 0
}
