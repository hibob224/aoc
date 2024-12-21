package y2019.day03

import utils.Point
import utils.getInputFile
import kotlin.math.abs

fun main() {
    println("Part one: ${Day3.solvePartOne()}")
    println("Part two: ${Day3.solvePartTwo()}")
}

object Day3 {

    private val lineOne = mutableListOf(Wire(0, 0))
    private val lineTwo = mutableListOf(Wire(0, 0))

    init {
        val lineOneInput = parseInput()[0]
        val lineTwoInput = parseInput()[1]
        lineOneInput.forEach { lineOne.addAll(lineOne.last().move(it)) }
        lineTwoInput.forEach { lineTwo.addAll(lineTwo.last().move(it)) }
        lineOne.removeAt(0)
        lineTwo.removeAt(0)
    }

    private fun parseInput(): List<List<String>> =
        getInputFile().readLines().map { it.split(",") }

    fun solvePartOne(): String = lineOne.intersect(lineTwo.toSet()).minOfOrNull { it.distanceFromOrigin() }.toString()

    fun solvePartTwo(): String = lineOne.intersect(lineTwo.toSet()).minOfOrNull { intersect ->
        lineOne.first { intersect == it }.steps + lineTwo.first { intersect == it }.steps
    }.toString()

    // Basically just Point, but have copied in this solution years later so, not fixing it now
    private data class Wire(val x: Int, val y: Int) {

        var steps = 0

        fun move(movement: String): List<Wire> {
            val direction = movement.first()
            val steps = movement.substring(1).toInt()

            return (1..steps).map { step ->
                when (direction) {
                    'R' -> copy(x = x + step)
                    'L' -> copy(x = x - step)
                    'U' -> copy(y = y - step)
                    'D' -> copy(y = y + step)
                    else -> throw IllegalArgumentException("Unknown movement type")
                }.also { it.steps += this.steps + step }
            }
        }

        fun distanceFromOrigin(): Int = manhattan(Point(0, 0))

        fun manhattan(other: Point): Int = abs(x - other.x) + abs(y - other.y)
    }
}
