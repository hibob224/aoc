package y2024.day18

import utils.Point
import utils.getInputFile
import utils.shortestPath

fun main() {
    println("Part one: " + Day18.solvePartOne())
    println("Part two: " + Day18.solvePartTwo())
}

object Day18 {

    private val example = false
    private val gridSize = if (example) 6 else 70
    private val p1Bytes = if (example) 12 else 1024
    private val input = getInputFile()
        .readLines()
        .map {
            val (x, y) = it.split(",").map(String::toInt)
            Point(x, y) to '#'
        }

    fun solvePartOne(): Int {
        val grid = input.take(p1Bytes).toMap()

        return grid.shortestPath(
            start = Point(0, 0),
            target = Point(gridSize, gridSize),
        ) { _, new, _ ->
            if (new in grid) -1 else 1
        }.second
    }

    fun solvePartTwo(): String {
        val start = Point(0, 0)
        val target = Point(gridSize, gridSize)
        var lastByte = Point(0, 0) // Keep track of the last byte added to the grid
        var shortestPath = emptyList<Point>() // Keep track of the current shortest path

        runCatching { // shortestPath throws when no path is found, we'll use that as our exit from the infinite loop below, so catch it...
            val grid = input.take(p1Bytes).toMap().toMutableMap() // Start with the first kb from P1, as we know a path is possible
            val remainingBytes = input.subList(p1Bytes, input.size) // List of the remaining undropped bytes
            var byteIndex = 0

            while (true) {
                val next = remainingBytes[byteIndex]
                lastByte = next.first
                grid[next.first] = next.second // Add next byte to our grid
                // If the new byte is part of our current shortest path, recalc our shortest path as it will need to change. This will throw
                // once there is no valid path to our target
                if (next.first in shortestPath || shortestPath.isEmpty()) {
                    shortestPath = grid.shortestPath(start, target) { _, new, _ -> if (new in grid) -1 else 1 }.first
                }
                byteIndex++
            }
        }

        return "${lastByte.x},${lastByte.y}"
    }
}
