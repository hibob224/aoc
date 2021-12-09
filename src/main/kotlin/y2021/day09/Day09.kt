package y2021.day09

import utils.Point
import java.io.File

fun main() {
    println("Part one: ${Day09.solvePartOne()}")
    println("Part two: ${Day09.solvePartTwo()}")
}

object Day09 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val possibleMoves = listOf(Point(x = 0, y = -1), Point(x = 0, y = 1), Point(x = -1, y = 0), Point(x = 1, y = 0))
    private fun parseInput(): List<String> =
        File("src/main/kotlin/$directory/input.txt").readLines()

    fun solvePartOne(): Int {
        val input = parseInput()
        val lowPoints = mutableListOf<Int>()

        repeat(input.first().length) { x ->
            repeat(input.size) { y ->
                val height = input[y][x].toString().toInt()
                if (
                    possibleMoves.all { move ->
                        height < (input.getOrNull(y + move.y)?.getOrNull(x + move.x)?.toString()?.toInt()
                            ?: Integer.MAX_VALUE)
                    }
                ) {
                    lowPoints += height
                }
            }
        }

        return lowPoints.sum() + lowPoints.size
    }

    fun solvePartTwo(): Int {
        val input = parseInput()
        val map = mutableMapOf<Point, Int>()

        repeat(input.first().length) { x ->
            repeat(input.size) { y ->
                map[Point(x, y)] = input[y][x].toString().toInt()
            }
        }

        val lowPoints = mutableListOf<Point>()

        map.forEach {
            val point = it.key
            val height = it.value

            val lowPoint = possibleMoves.all { move ->
                height < map.getOrDefault(point.copy(x = point.x + move.x, y = point.y + move.y), Integer.MAX_VALUE)
            }
            if (lowPoint) {
                lowPoints += point
            }
        }

        return lowPoints.map {
            val basinArea = mutableSetOf<Point>()
            val locations = ArrayDeque(listOf(it))

            while (locations.isNotEmpty()) {
                val point = locations.removeFirst()

                possibleMoves.forEach { move ->
                    val checkPoint = point.copy(x = point.x + move.x, y = point.y + move.y)
                    if (checkPoint !in basinArea && map.getOrDefault(checkPoint, 9) != 9) {
                        // Valid space, expand
                        basinArea += checkPoint
                        locations.addFirst(checkPoint)
                    }
                }
            }
            basinArea.size
        }.sortedDescending().take(3).reduce { acc, i -> acc * i }
    }
}