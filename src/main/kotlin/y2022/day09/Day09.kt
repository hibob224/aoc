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

    private val input = File("src/main/kotlin/$directory/input.txt").readLines()
    private val commandRegex = """^(.) (\d+)$""".toRegex()

    fun solvePartOne(): Int = fall(2)

    fun solvePartTwo(): Int = fall(10)

    private fun fall(knots: Int): Int {
        var headPosition = Point(0, 0)
        var ropePositions = Array(knots.dec()) { Point(0, 0) }.toList()
        val tailVisited = mutableSetOf(ropePositions.last())

        input.forEach {
            val (_, direction, distance) = commandRegex.find(it)!!.groupValues

            repeat(distance.toInt()) {
                when (direction) {
                    "U" -> headPosition = headPosition.copy(y = headPosition.y - 1)
                    "D" -> headPosition = headPosition.copy(y = headPosition.y + 1)
                    "L" -> headPosition = headPosition.copy(x = headPosition.x - 1)
                    "R" -> headPosition = headPosition.copy(x = headPosition.x + 1)
                }
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
