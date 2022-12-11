package template

import java.io.File

fun main() {
    println("Part one: ${DayXX.solvePartOne()}")
    println("Part two: ${DayXX.solvePartTwo()}")
}

object DayXX {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val input = File("src/main/kotlin/$directory/input.txt").readLines()

    fun solvePartOne(): Int = 0

    fun solvePartTwo(): Int = 0
}
