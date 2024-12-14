package y2024.day14

import utils.Point
import utils.getInputFile
import java.io.File
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

    fun solvePartOne(): Int {
        val endPositions = input
            .map { (start, velocity) ->
                (0 until 100).fold(start) { acc, _ ->
                    move(acc, velocity)
                }
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

        return quadrants.fold(1) { acc, i -> acc * i}
    }

    fun solvePartTwo(): Long {
        val out = (0 until 8050).fold(input) { acc, i ->
            acc.map { (pos, v) ->
                move(pos, v) to v
            }
        }
        printOutput(out.map { it.first }.toSet())
        return 8050
    }

    private fun move(pos: Point, v: Point): Point {
        val newX = if (pos.x + v.x < 0) {
            width - (v.x.absoluteValue - pos.x)
        } else if (pos.x + v.x >= width) {
            v.x - (width - pos.x)
        } else {
            pos.x + v.x
        }
        val newY = if (pos.y + v.y < 0) {
            height - (v.y.absoluteValue - pos.y)
        } else if (pos.y + v.y >= height) {
            v.y - (height - pos.y)
        } else {
            pos.y + v.y
        }
        return Point(newX, newY)
    }

    private fun printOutput(robots: Set<Point>) {
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
