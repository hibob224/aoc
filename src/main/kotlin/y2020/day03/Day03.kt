package y2020.day03

import utils.Point
import utils.getInputFile

fun main() {
    println("Part one: ${Day03.solvePartOne()}")
    println("Part two: ${Day03.solvePartTwo()}")
}

object Day03 {

    private val input: Map<Point, Boolean>
    private val width: Int
    private val height: Int

    init {
        input = getInputFile()
            .readLines()
            .also {
                height = it.size
                width = it.first().length
            }
            .mapIndexed { y, s ->
                s.mapIndexed { x, c ->
                    Point(x, y) to (c == '#')
                }
            }
            .flatten()
            .toMap()
    }

    fun solvePartOne(): String {
        return numberOfTreeHits(3, 1).toString()
    }

    fun solvePartTwo(): String {
        return listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
            .map { numberOfTreeHits(it.first, it.second) }
            .fold(1L) { acc, i -> acc * i }
            .toString()
    }

    private fun numberOfTreeHits(xSlope: Int, ySlope: Int): Long {
        var currentPos = Point(0, 0)
        val treesHit = mutableListOf<Point>()
        while (currentPos.y <= height) {
            val newX = (currentPos.x + xSlope) % width
            currentPos = Point(newX, currentPos.y + ySlope)
            if (input[currentPos] == true) {
                treesHit.add(currentPos)
            }
        }
        return treesHit.size.toLong()
    }
}