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
    private val input = getInputFile(this::class.java.packageName, example = example).readLines()

    private fun parseInput(bytes: Int) =
        input
            .take(bytes)
            .associate {
                val (x, y) = it.split(",").map(String::toInt)
                Point(x, y) to '#'
            }


    fun solvePartOne(): Int {
        val grid = parseInput(p1Bytes)

        return grid.shortestPath(
            start = Point(0, 0),
            target = Point(gridSize, gridSize),
        ) { _, new, _ ->
            if (new in grid) -1 else 1
        }.second
    }

    fun solvePartTwo(): String {
        var bytes = gridSize
        val start = Point(0, 0)
        val target = Point(gridSize, gridSize)

        runCatching {
            while (true) {
                val grid = parseInput(bytes)
                grid.shortestPath(start, target) { _, new, _ -> if (new in grid) -1 else 1 }
                bytes++
            }
        }

        return parseInput(bytes).keys.last().let { "${it.x},${it.y}" }
    }
}
