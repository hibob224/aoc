package day03

import utils.Point
import java.io.File

fun main() {
    println("Part one: ${Day03.solvePartOne()}")
    println("Part two: ${Day03.solvePartTwo()}")
}

object Day03 {

    private val directory: String
        get() = this::class.java.`package`.name

    private val input = parseInput()
    private fun parseInput(): Map<Point, Boolean> =
        File("src/main/kotlin/$directory/input.txt")
            .readLines()
            .mapIndexed { y, s ->
                s.mapIndexed { x, c ->
                    Point(x, y) to (c == '#')
                }
            }
            .flatten()
            .toMap()


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
        val targetY = input.keys.maxByOrNull { it.y }!!.y
        val maxX = input.keys.maxByOrNull { it.x }!!.x
        var currentPos = Point(0, 0)
        val treesHit = mutableListOf<Point>()

        while (currentPos.y <= targetY) {
            var newX = currentPos.x + xSlope
            val newY = currentPos.y + ySlope
            if (newX > maxX) {
                newX -= maxX.inc()
            }
            currentPos = Point(newX, newY)
            if (input[currentPos] == true) {
                treesHit.add(currentPos)
            }
        }

        return treesHit.size.toLong()
    }
}