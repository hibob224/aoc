package y2022.day09

import utils.Point
import java.io.File

fun main() {
    println("Part one: ${Day09.solvePartOne()}")
    println("Part two: ${Day09.solvePartTwo()}")
}

object Day09 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val commandRegex = """^(.) (\d+)$""".toRegex()
    private val input = File("src/main/kotlin/$directory/input.txt")
        .readLines()
        .map {
            val (_, direction, distance) = commandRegex.find(it)!!.groupValues
            direction to distance
        }
    private val directions = mapOf(
        "U" to Point(0, -1),
        "D" to Point(0, 1),
        "L" to Point(-1, 0),
        "R" to Point(1, 0)
    )

    fun solvePartOne(): Int = fall(2)

    fun solvePartTwo(): Int = fall(10)

    private fun fall(knots: Int): Int {
        var headPosition = Point(0, 0)
        var ropePositions = List(knots.dec()) { Point(0, 0) }
        val tailVisited = mutableSetOf(ropePositions.last())

        input.forEach {
            val (direction, distance) = it
            repeat(distance.toInt()) {
                headPosition += directions[direction]!!
                ropePositions = ropePositions.fold(listOf(headPosition)) { acc, segment ->
                    return@fold if (segment.euclidean(acc.last()) >= 2) {
                        acc + segment.getNeighbours(diagonal = true).minBy { it.euclidean(acc.last()) }
                    } else {
                        acc + segment
                    }
                }.drop(1)

                tailVisited += ropePositions.last()
            }
        }

        return tailVisited.size
    }
}
