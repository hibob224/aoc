package y2021.day05

import utils.Point
import utils.getInputFile

fun main() {
    println("Part one: ${Day05.solvePartOne()}")
    println("Part two: ${Day05.solvePartTwo()}")
}

object Day05 {

    private val parseRegex = """^(\d+),(\d+) -> (\d+),(\d+)""".toRegex()
    private val input: List<Pair<Point, Point>> =
        getInputFile()
            .readLines()
            .map {
                val matches = parseRegex.find(it)!!.groupValues
                Point(matches[1].toInt(), matches[2].toInt()) to Point(matches[3].toInt(), matches[4].toInt())
            }

    fun solvePartOne(): Int {
        val board = mutableMapOf<Point, Int>()

        input.forEach {
            if (it.first.x != it.second.x && it.first.y != it.second.y) return@forEach // No diagonals pls
            it.first.x.toward(it.second.x).forEach { x ->
                it.first.y.toward(it.second.y).forEach { y ->
                    val point = Point(x, y)
                    board[point] = board.getOrDefault(point, 0) + 1
                }
            }
        }

        return board.values.count { it > 1 }
    }

    fun solvePartTwo(): Int {
        val board = mutableMapOf<Point, Int>()

        input.forEach {
            val xRange = (it.first.x toward it.second.x).toList()
            val yRange = (it.first.y toward it.second.y).toList()
            repeat(maxOf(xRange.size, yRange.size)) {
                val point = Point(xRange.getOrElse(it) { xRange.last() }, yRange.getOrElse(it) { yRange.last() })
                board[point] = board.getOrDefault(point, 0) + 1
            }
        }

        return board.values.count { it > 1 }
    }

    private infix fun Int.toward(to: Int): IntProgression {
        val step = if (this > to) -1 else 1
        return IntProgression.fromClosedRange(this, to, step)
    }
}
