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

    private val possibleMoves = listOf(
        Point(x = 0, y = -1),
        Point(x = 0, y = 1),
        Point(x = -1, y = 0),
        Point(x = 1, y = 0)
    )

    private fun parseInput(): List<String> =
        File("src/main/kotlin/$directory/input.txt").readLines()

    private val input: MutableMap<Point, Int> =
        File("src/main/kotlin/$directory/input.txt")
            .readLines()
            .flatMapIndexed { y, row ->
                row.mapIndexed { x, c ->
                    Point(x, y) to c.toString().toInt()
                }
            }.toMap().toMutableMap()


    fun solvePartOne(): Int = input
        .filter {
            val (point, height) = it
            possibleMoves.all { move ->
                height < input.getOrDefault(point.copy(x = point.x + move.x, y = point.y + move.y), Integer.MAX_VALUE)
            }
        }.let {
            it.values.sum() + it.size
        }

    fun solvePartTwo(): Int {
        // Get the low points
        val lowPoints = input.filter {
            val (point, height) = it
            possibleMoves.all { move ->
                height < input.getOrDefault(point.copy(x = point.x + move.x, y = point.y + move.y), Integer.MAX_VALUE)
            }
        }.keys

        return lowPoints.map {
            val basinArea = mutableSetOf<Point>()
            val locations = ArrayDeque(listOf(it))

            while (locations.isNotEmpty()) {
                val point = locations.removeFirst()

                // Go through every move from this point, if we haven't seen the new point before and it isn't a ridge
                // (value 9), then add it to the basinArea and list of locations to check
                possibleMoves.forEach { move ->
                    val checkPoint = point.copy(x = point.x + move.x, y = point.y + move.y)
                    if (checkPoint !in basinArea && input.getOrDefault(checkPoint, 9) != 9) {
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