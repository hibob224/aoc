package y2022.day14

import utils.Point
import java.io.File

fun main() {
    println("Part one: ${Day14.solvePartOne()}")
    println("Part two: ${Day14.solvePartTwo()}")
}

object Day14 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val regex = """(\d+,\d+)""".toRegex()
    private val input = File("src/main/kotlin/$directory/input.txt")
        .readLines()
        .map {
            regex.findAll(it)
                .flatMap { it.groupValues.drop(1) }
                .map {
                    val (x, y) = it.split(",", limit = 2)
                    Point(x.toInt(), y.toInt())
                }.toList()
        }
        .flatMap { line ->
            line.windowed(2)
                .flatMap { (start, end) ->
                    if (start.x != end.x) {
                        if (start.x < end.x) {
                            (start.x..end.x).map { x -> Point(x, start.y) }
                        } else {
                            (start.x downTo end.x).map { x -> Point(x, start.y) }
                        }
                    } else {
                        if (start.y < end.y) {
                            (start.y..end.y).map { y -> Point(start.x, y) }
                        } else {
                            (start.y downTo end.y).map { y -> Point(start.x, y) }
                        }
                    }
                }
                .map { it to Block.WALL }
        }
        .toTypedArray()
    private val validMoves = listOf(Point(0, 1), Point(-1, 1), Point(1, 1))

    fun solvePartOne(): Int {
        val grid = mutableMapOf(*input)
        val lowestWall = grid.maxOf { it.key.y }

        outer@ while (true) {
            var sand = Point(500, 0)
            while (true) {
                val freeSpot = validMoves
                    .asSequence()
                    .map { sand + it }
                    .firstOrNull { !grid.containsKey(it) } ?: break // Break if there are no more valid moves for this block of sand
                if (freeSpot.y >= lowestWall) {
                    break@outer // Sand will now fall infinitely, there are no more walls to block it, break the outer loop, we're finished
                }
                sand = freeSpot
            }
            grid[sand] = Block.SAND
        }


        return grid.count { it.value == Block.SAND }
    }

    fun solvePartTwo(): Int {
        val grid = mutableMapOf(*input)
        val floor = grid.maxOf { it.key.y } + 2

        outer@ while (!grid.containsKey(Point(500, 0))) { // Loop until sand source is blocked
            var sand = Point(500, 0)
            while (true) {
                val freeSpot = validMoves
                    .asSequence()
                    .map { sand + it }
                    .firstOrNull { !grid.containsKey(it) } ?: break // Break if there are no more valid moves for this block of sand
                if (freeSpot.y == floor) {
                    break // Next valid move would be into the infinite floor, break
                }
                sand = freeSpot
            }
            grid[sand] = Block.SAND
        }

        return grid.count { it.value == Block.SAND }
    }

    enum class Block {
        WALL, SAND
    }
}
