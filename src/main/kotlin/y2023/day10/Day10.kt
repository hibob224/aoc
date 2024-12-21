package y2023.day10

import utils.Point
import utils.getInputFile

fun main() {
    println("Part one: ${Day10.solvePartOne()}")
    println("Part two: ${Day10.solvePartTwo()}")
}

object Day10 {

    private val start: Point
    private val input =
        getInputFile()
            .readLines()
            .flatMapIndexed { y, s ->
                s.mapIndexed { x, c ->
                    Point(x, y) to c
                }
            }
            .toMap()
            .also {
                start = it.entries.find { it.value == 'S' }?.key!!
            }
            .filterValues { it != '.' && it != 'S' }
            .map {
                it.key to when (it.value) {
                    '|' -> Pipe.Vertical
                    '-' -> Pipe.Horizontal
                    'L' -> Pipe.BendNE
                    'J' -> Pipe.BendNW
                    'F' -> Pipe.BendSE
                    '7' -> Pipe.BendSW
                    else -> error("ILLEGAL")
                }
            }.toMap()

    fun solvePartOne(): Long {
        var (current, direction) = firstMove()
        var steps = 1L

        do {
            val (newPoint, newDirection) = input[current]!!.transit(current, direction)
            current = newPoint
            direction = newDirection
            steps++
        } while (current != start)

        return steps / 2
    }

    fun solvePartTwo(): Long = 0

    private fun firstMove(): Pair<Point, Char> = when {
        input[start.n] in listOf(Pipe.Vertical, Pipe.BendSW, Pipe.BendSE) -> start.n to 'N'
        input[start.e] in listOf(Pipe.Horizontal, Pipe.BendNW, Pipe.BendSW) -> start.e to 'E'
        input[start.s] in listOf(Pipe.Vertical, Pipe.BendNW, Pipe.BendSE) -> start.s to 'S'
        input[start.w] in listOf(Pipe.Horizontal, Pipe.BendSE, Pipe.BendNE) -> start.w to 'W'
        else -> error("Illegal")
    }

    sealed class Pipe {
        data object Vertical : Pipe() {
            override fun transit(current: Point, direction: Char): Pair<Point, Char> = when (direction) {
                'N' -> current.n to 'N'
                'S' -> current.s to 'S'
                else -> error("ILLEGAL VERTICAL DIRECTION: $direction")
            }
        }

        data object Horizontal : Pipe() {
            override fun transit(current: Point, direction: Char): Pair<Point, Char> = when (direction) {
                'E' -> current.copy(x = current.x.inc()) to 'E'
                'W' -> current.copy(x = current.x.dec()) to 'W'
                else -> error("ILLEGAL HORIZONTAL DIRECTION: $direction")
            }
        }

        data object BendNE : Pipe() {
            override fun transit(current: Point, direction: Char): Pair<Point, Char> = when (direction) {
                'S' -> current.e to 'E'
                'W' -> current.n to 'N'
                else -> error("ILLEGAL L DIRECTION: $direction")
            }
        }

        data object BendNW : Pipe() {
            override fun transit(current: Point, direction: Char): Pair<Point, Char> = when (direction) {
                'E' -> current.n to 'N'
                'S' -> current.w to 'W'
                else -> error("ILLEGAL J DIRECTION: $direction")
            }
        }

        data object BendSW : Pipe() {
            override fun transit(current: Point, direction: Char): Pair<Point, Char> = when (direction) {
                'E' -> current.s to 'S'
                'N' -> current.w to 'W'
                else -> error("ILLEGAL 7 DIRECTION: $direction")
            }
        }

        data object BendSE : Pipe() {
            override fun transit(current: Point, direction: Char): Pair<Point, Char> = when (direction) {
                'N' -> current.e to 'E'
                'W' -> current.s to 'S'
                else -> error("ILLEGAL F DIRECTION: $direction")
            }
        }

        abstract fun transit(current: Point, direction: Char): Pair<Point, Char>
    }
}
