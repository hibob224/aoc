package y2021.day20

import utils.Point
import utils.isEven
import java.io.File

fun main() {
    println("Part one: ${Day20.solvePartOne()}")
    println("Part two: ${Day20.solvePartTwo()}")
}

object Day20 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private fun parseInput(): List<String> =
        File("src/main/kotlin/$directory/input.txt").readLines()

    fun solvePartOne(): Int {
        val input = parseInput()
        val algorithm = input.first()
        val grid = mutableMapOf<Point, Char>()

        input.drop(2).forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                grid[Point(x, y)] = c
            }
        }

        val moves = listOf(
            Point(x = -1, y = -1), Point(x = 0, y = -1), Point(x = 1, y = -1),
            Point(x = -1, y = 0), Point(x = 0, y = 0), Point(x = 1, y = 0),
            Point(x = -1, y = 1), Point(x = 0, y = 1), Point(x = 1, y = 1)
        )

        var default = '.'

        repeat(2) {
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
                        when (gridCopy.getOrDefault(p, default)) {
                            '#' -> 1
                            '.' -> 0
                            else -> throw IllegalStateException("Unknown character")
                        }
                    }.joinToString("").toInt(2)
                    grid[point] = algorithm[valueIndex]
                }
            }
            default = algorithm[if (default == '.') 0 else algorithm.lastIndex]
        }

        return grid.values.count { it == '#' }
    }

    fun solvePartTwo(): Int {
        return -1
    }

    private fun printGrid(grid: Map<Point, Char>, default: Char = '.') {
        val lowX = grid.minOf { it.key.x } - 1
        val hiX = grid.maxOf { it.key.x } + 1
        val lowY = grid.minOf { it.key.y } - 1
        val hiY = grid.maxOf { it.key.y } + 1

        (lowY..hiY).forEach { y ->
            val row = (lowX..hiX)
                .map { grid.getOrDefault(Point(it, y), default) }
                .joinToString("")
            println(row)
        }
    }
}