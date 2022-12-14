package y2021.day20

import utils.Point
import utils.getInputFile

fun main() {
    println("Part one: ${Day20.solvePartOne()}")
    println("Part two: ${Day20.solvePartTwo()}")
}

object Day20 {

    private val algorithm: String
    private val initialImage: Map<Point, Boolean>


    init {
        val input = getInputFile(this::class.java.packageName).readLines()
        algorithm = input.first()
        initialImage = input.drop(2).flatMapIndexed { y: Int, row: String ->
            row.mapIndexed { x, c ->
                Point(x, y) to (c == '#')
            }
        }.toMap()
    }

    fun solvePartOne(): Int = enhance(algorithm, initialImage, 2)

    fun solvePartTwo(): Int = enhance(algorithm, initialImage, 50)

    private fun enhance(algorithm: String, grid: Map<Point, Boolean>, iterations: Int): Int {
        val moves = listOf(
            Point(x = -1, y = -1), Point(x = 0, y = -1), Point(x = 1, y = -1),
            Point(x = -1, y = 0), Point(x = 0, y = 0), Point(x = 1, y = 0),
            Point(x = -1, y = 1), Point(x = 0, y = 1), Point(x = 1, y = 1)
        )
        val grid = grid.toMutableMap()

        var default = false

        repeat(iterations) {
            val lowX = grid.minOf { it.key.x } - 1
            val hiX = grid.maxOf { it.key.x } + 1
            val lowY = grid.minOf { it.key.y } - 1
            val hiY = grid.maxOf { it.key.y } + 1
            val gridCopy = grid.toMap()

            (lowX..hiX).forEach { x ->
                (lowY..hiY).forEach { y ->
                    val point = Point(x, y)
                    val valueIndex = moves.map {
                        val p = point.copy(x = point.x + it.x, y = point.y + it.y)
                        if (gridCopy.getOrDefault(p, default)) 1 else 0
                    }.joinToString("").toInt(2)
                    grid[point] = algorithm[valueIndex] == '#'
                }
            }
            default = algorithm[if (!default) 0 else algorithm.lastIndex] == '#'
        }

        return grid.values.count { it }
    }
}