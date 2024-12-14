package y2024.day14

import utils.Point
import utils.getInputFile
import utils.pointsInArea
import kotlin.math.absoluteValue

fun main() {
    println("Part one: " + Day14.solvePartOne())
    println("Part two: " + Day14.solvePartTwo())
}

object Day14 {

    //p=0,4 v=3,-3
    private val example = false

    private val parse = """^p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)$""".toRegex()
    private val input = getInputFile(this::class.java.packageName, example = example)
        .readLines()
        .map {
            val (px, py, vx, vy) = parse.find(it)!!.destructured
            Point(px.toInt(), py.toInt()) to Point(vx.toInt(), vy.toInt())
        }
    private val width = if (example) 11 else 101
    private val height = if (example) 7 else 103

    fun solvePartOne(): Long {
        val endPositions = input.map { (start, velocity) ->
            (0 until 100).fold(start) { acc, _ ->
                val newX = if (acc.x + velocity.x < 0) {
                    width - (velocity.x.absoluteValue - acc.x)
                } else if (acc.x + velocity.x >= width) {
                    velocity.x - (width - acc.x)
                } else {
                    acc.x + velocity.x
                }
                val newY = if (acc.y + velocity.y < 0) {
                    height - (velocity.y.absoluteValue - acc.y)
                } else if (acc.y + velocity.y >= height) {
                    velocity.y - (height - acc.y)
                } else {
                    acc.y + velocity.y
                }

                Point(newX, newY)
            }
        }.groupingBy { it }.eachCount()

        val qx = width.dec() / 2
        val qy = height.dec() / 2
        val q1 = pointsInArea(Point(0, 0), Point(qx - 1, qy - 1))
        val q2 = pointsInArea(Point(qx + 1, 0), Point(width - 1, qy - 1))
        val q3 = pointsInArea(Point(0, qy + 1), Point(qx - 1, height - 1))
        val q4 = pointsInArea(Point(qx + 1, qy + 1), Point(width - 1, height - 1))
        val quadrants = listOf(q1, q2, q3, q4)

        printOutput(endPositions.keys, 1)

        return quadrants
            .map { q ->
                endPositions.entries.filter { it.key in q }.sumOf { it.value }
            }.also(::println).fold(1) { acc, i -> acc * i }
    }

    fun solvePartTwo(): Long {
        return 0
    }

    fun printOutput(robots: Set<Point>, iteration: Int) {
        val out = buildString {
            (0 until height).forEach { y ->
                (0 until width).forEach { x ->
                    append(if (Point(x, y) in robots) 'X' else ' ')
                }
                appendLine()
            }
        }

    }
}
