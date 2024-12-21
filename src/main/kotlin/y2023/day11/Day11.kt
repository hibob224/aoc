package y2023.day11

import utils.Point
import utils.combinations
import utils.getInputFile

fun main() {
    println("Part one: ${Day11.solvePartOne()}")
    println("Part two: ${Day11.solvePartTwo()}")
}

object Day11 {

    private val input =
        getInputFile()
            .readLines()
            .flatMapIndexed { y, line ->
                line.mapIndexed { x, c -> Point(x, y) to c }
            }.toMap()

    fun solvePartOne(): Long = solve(1)

    fun solvePartTwo(): Long = solve(999_999)

    private fun solve(multiplier: Long): Long {
        val maxY = input.maxOf { it.key.y }
        val maxX = input.maxOf { it.key.x }

        val emptyRows = (0..maxY).filter { y ->
            input.filterKeys { it.y == y }.all { it.value == '.' }
        }
        val emptyCols = (0..maxX).filter { x ->
            input.filterKeys { it.x == x }.all { it.value == '.' }
        }

        return input
            .filterValues { it == '#' }
            .keys
            .combinations(2)
            .map {
                val (pointA, pointB) = it
                val manhattan = pointA.manhattan(pointB)
                val extraRows = emptyRows.count { it in minOf(pointA.y, pointB.y)..maxOf(pointA.y, pointB.y) }.toLong()
                val extraCols = emptyCols.count { it in minOf(pointA.x, pointB.x)..maxOf(pointA.x, pointB.x) }.toLong()
                manhattan + (extraRows * multiplier) + (extraCols * multiplier)
            }.sum()
    }
}
