package template

import utils.getInputFile

fun main() {
    println("Part one: ${DayXX.solvePartOne()}")
    println("Part two: ${DayXX.solvePartTwo()}")
}

object DayXX {

    private val input = getInputFile(this::class.java.packageName).readLines()

    fun solvePartOne(): Int = 0

    fun solvePartTwo(): Int = 0
}
