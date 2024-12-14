package y2024.day14

import utils.Point
import utils.getInputFile
import utils.product
import java.io.File

fun main() {
    println("Part one: " + Day14.solvePartOne())
    println("Part two: " + Day14.solvePartTwo())
}

object Day14 {

    private val parse = """^p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)$""".toRegex()
    private val input = getInputFile(this::class.java.packageName)
        .readLines()
        .map {
            val (px, py, vx, vy) = parse.find(it)!!.destructured
            Point(px.toInt(), py.toInt()) to Point(vx.toInt(), vy.toInt())
        }
    private const val width = 101
    private const val height = 103

    fun solvePartOne(): Int {
        val endPositions = input
            .map { (start, velocity) ->
                move(start, velocity, 100)
            }.groupingBy { it }.eachCount()

        val qx = width.dec() / 2
        val qy = height.dec() / 2
        val quadrants = MutableList(4) { 0 }

        endPositions.forEach { (pos, c) ->
            if (pos.x < qx && pos.y < qy) quadrants[0] += c
            if (pos.x > qx && pos.y < qy) quadrants[1] += c
            if (pos.x < qx && pos.y > qy) quadrants[2] += c
            if (pos.x > qx && pos.y > qy) quadrants[3] += c
        }

        return quadrants.product()
    }

    fun solvePartTwo(): Int {
        return generateSequence(input) { bots ->
            // Assuming that if all bots are in a unique position, we probably have the tree
            if (bots.groupingBy { it.first }.eachCount().count() == input.size) return@generateSequence null
            bots.map { (pos, v) -> move(pos, v, 1) to v }
        }.also {
            printOutput(it.last().map(Pair<Point, Point>::first).toSet())
        }.count().dec() // -1 to account for the initial state
    }

    private fun move(pos: Point, v: Point, moves: Int): Point {
        val newX = (pos.x + v.x * moves) % width
        val newY = (pos.y + v.y * moves) % height
        return Point(newX + if (newX < 0) width else 0, newY + if (newY < 0) height else 0)
    }

    private fun printOutput(robots: Set<Point>) {
        if (System.getenv("CI").toBoolean()) return
        val out = buildString {
            (0 until height).forEach { y ->
                (0 until width).forEach { x ->
                    append(if (Point(x, y) in robots) 'X' else ' ')
                }
                appendLine()
            }
        }
        File("src/main/kotlin/${this::class.java.packageName.replace('.', '/')}/out.txt")
            .outputStream()
            .use {
                it.write(out.toByteArray())
            }
    }
}
