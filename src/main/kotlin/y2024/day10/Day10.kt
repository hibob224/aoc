package y2024.day10

import utils.Point
import utils.getInputFile
import utils.toPointGrid

fun main() {
    println("Part one: " + Day10.solvePartOne())
    println("Part two: " + Day10.solvePartTwo())
}

object Day10 {

    private val input =
        getInputFile()
            .readLines()
            .toPointGrid { _, c -> c.digitToInt() }

    fun solvePartOne(): Int {
        return input
            .filterValues { it == 0 }
            .keys
            .sumOf { trailheads(it).toSet().size }
    }

    fun solvePartTwo(): Int {
        return input
            .filterValues { it == 0 }
            .keys
            .sumOf { trailheads(it).size }
    }

    private fun trailheads(point: Point): List<Point> {
        val currentHeight = input[point]!!
        val neighbours = point
            .getNeighbours()
            .filter { input[it] == currentHeight + 1 }

        return if (currentHeight == 8) {
            neighbours
        } else {
            neighbours.flatMap { trailheads(it) }
        }
    }
}
