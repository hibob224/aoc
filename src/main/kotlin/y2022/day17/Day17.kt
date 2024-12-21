package y2022.day17

import utils.Point
import utils.getInputFile
import utils.orZero
import java.io.File

fun main() {
    println("Part one: ${Day17.solvePartOne()}")
    println("Part two: ${Day17.solvePartTwo()}")
}

object Day17 {

    private val input = getInputFile().readText()
    private val rockShapes = listOf(
        listOf(Point(0, 0), Point(1, 0), Point(2, 0), Point(3, 0)), // -
        listOf(Point(1, 0), Point(0, 1), Point(1, 1), Point(2, 1), Point(1, 2)), // +
        listOf(Point(0, 0), Point(1, 0), Point(2, 0), Point(2, 1), Point(2, 2)), // backwards L
        listOf(Point(0, 0), Point(0, 1), Point(0, 2), Point(0, 3)), // |
        listOf(Point(0, 0), Point(1, 0), Point(0, 1), Point(1, 1)), // []
    )
    private val jetMovements = mapOf(
        '<' to Point(-1, 0),
        '>' to Point(1, 0),
    )
    private val downMovement = Point(0, -1)

    fun solvePartOne(): Int {
        val fallenRocks = mutableListOf<Point>()
        val horizontalBounds = 0..6
        var jetIndex = 0

        val blowRock = { rock: List<Point> ->
            val direction = input[jetIndex % input.length]
            val jetMovement = jetMovements[direction]!!
            jetIndex++
            rock
                .map { it + jetMovement }
                .takeIf {
                    it.none { it in fallenRocks } && it.maxOf(Point::x) in horizontalBounds && it.minOf(Point::x) in horizontalBounds
                }
                ?: rock
        }

        repeat(2022) { rockIndex ->
            // Figure out start point...
            val startPoint = Point(2, fallenRocks.maxOfOrNull(Point::y)?.inc().orZero() + 3)
            var newRock = rockShapes[rockIndex % rockShapes.size]
                .map { startPoint + it }

            while (true) {
                newRock = blowRock(newRock)
                val newPosition = newRock.map { it + downMovement }
                if (newPosition.any { it in fallenRocks || it.y < 0 }) {
                    break
                }
                newRock = newPosition
            }

            fallenRocks += newRock
        }

        return fallenRocks.maxOf(Point::y).inc()
    }

    fun solvePartTwo(): Int {
        // Simulate a whole bunch of rocks
        // Find a repeating cycle in the grid
        // Do math to calculate height after 1_000_000_000_000 rocks
        return 0
    }

    fun drawGrid(points: List<Point>) {
        val maxY = points.maxOf(Point::y)

        val file = File("grid.txt")
        file.writeText("")
        val printWriter = file.printWriter()

        (maxY downTo 0).forEach { y ->
            (0..6).joinToString(separator = "") { x ->
                if (Point(x, y) in points) {
                    "#"
                } else {
                    "."
                }
            }.let(printWriter::println)
        }
        printWriter.close()
    }
}
