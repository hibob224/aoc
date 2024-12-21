package y2024.day04

import utils.Point
import utils.getInputFile
import utils.toPointGrid
import utils.traverse

fun main() {
    println("Part one: " + Day04.solvePartOne())
    println("Part two: " + Day04.solvePartTwo())
}

object Day04 {

    private val input = getInputFile().readLines().toPointGrid()

    fun solvePartOne(): Long {
        var count = 0L
        val allDirections = Point.neighbourDirections + Point.diagonalNeighbourDirections

        input.traverse { point, c ->
            if (c == 'X') {
                count += allDirections.count { part1Search('M', point, it) }
            }
        }

        return count
    }

    fun solvePartTwo(): Long {
        var count = 0L
        val directionPairs = listOf(
            Point(-1, -1) to Point(1, 1),
            Point(1, -1) to Point(-1, 1)
        )
        val validCombos = listOf('M' to 'S', 'S' to 'M')

        input.traverse { point, c ->
            if (input[point] == 'A') {
                val valid = directionPairs
                    .map { input[point + it.first] to input[point + it.second] }
                    .all { it in validCombos }
                if (valid) count++
            }
        }

        return count
    }

    private fun part1Search(targ: Char, currentPoint: Point, direction: Point): Boolean {
        val nextPoint = currentPoint + direction
        val nextChar = input[nextPoint]
        return if (nextChar == targ) {
            when (targ) {
                'M' -> part1Search('A', nextPoint, direction)
                'A' -> part1Search('S', nextPoint, direction)
                'S' -> true
                else -> false
            }
        } else {
            false
        }
    }
}
