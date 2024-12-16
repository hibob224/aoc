package y2024.day16

import utils.Point
import utils.getInputFile
import utils.toPointGrid
import java.util.*

fun main() {
    println("Part one: " + Day16.solvePartOne())
    println("Part two: " + Day16.solvePartTwo())
}

object Day16 {

    private val input = getInputFile(this::class.java.packageName, example = false)
        .readLines()
        .toPointGrid()

    fun solvePartOne(): Int {
        val start = input.entries.find { it.value == 'S' }!!.key
        val end = input.entries.find { it.value == 'E' }!!.key
        val movementQueue: PriorityQueue<Triple<Point, Direction, Int>> = PriorityQueue(compareBy { it.third })
        movementQueue += Triple(start, Direction.EAST, 0)

        val visited = mutableSetOf(start to Direction.EAST)
        val scores = mutableListOf<Int>()

        while (movementQueue.isNotEmpty()) {
            val (point, direction, score) = movementQueue.poll()
            visited += point to direction

            val n = point.n
            if (n == end) {
                scores += score + 1 + direction.rotationScore(Direction.NORTH)
            } else if ((n to Direction.NORTH) !in visited && !input.isWall(n)) {
                movementQueue += Triple(n, Direction.NORTH, score + 1 + direction.rotationScore(Direction.NORTH))
            }

            val e = point.e
            if (e == end) {
                scores += score + 1 + direction.rotationScore(Direction.EAST)
            } else if ((e to Direction.EAST) !in visited && !input.isWall(e)) {
                movementQueue += Triple(e, Direction.EAST, score + 1 + direction.rotationScore(Direction.EAST))
            }

            val s = point.s
            if (s == end) {
                scores += score + 1 + direction.rotationScore(Direction.SOUTH)
            } else if ((s to Direction.SOUTH) !in visited && !input.isWall(s)) {
                movementQueue += Triple(s, Direction.SOUTH, score + 1 + direction.rotationScore(Direction.SOUTH))
            }

            val w = point.w
            if (w == end) {
                scores += score + 1 + direction.rotationScore(Direction.WEST)
            } else if ((w to Direction.WEST) !in visited && !input.isWall(w)) {
                movementQueue += Triple(w, Direction.WEST, score + 1 + direction.rotationScore(Direction.WEST))
            }
        }

        return scores.min()
    }

    data class Path(
        val currentPoint: Point,
        val currentDirection: Direction,
        val score: Int,
        val visited: Set<Point>,
    )

    data class GoodPath(
        val score: Int,
        val path: Set<Point>,
    )

    fun solvePartTwo(): Int {
        val start = input.entries.find { it.value == 'S' }!!.key
        val end = input.entries.find { it.value == 'E' }!!.key
        val movementQueue: PriorityQueue<Path> = PriorityQueue(compareBy { it.score })
        movementQueue += Path(start, Direction.EAST, 0, setOf(start, end))

        val visited = mutableSetOf(start to Direction.EAST)
        val scores = mutableListOf<GoodPath>()

        while (movementQueue.isNotEmpty()) {
            val (point, direction, score, path) = movementQueue.poll()
            visited += point to direction

            val n = point.n
            if (n == end) {
                scores += GoodPath(score + 1 + direction.rotationScore(Direction.NORTH), path + n)
            } else if ((n to Direction.NORTH) !in visited && !input.isWall(n)) {
                movementQueue += Path(n, Direction.NORTH, score + 1 + direction.rotationScore(Direction.NORTH), path + n)
            }

            val e = point.e
            if (e == end) {
                scores += GoodPath(score + 1 + direction.rotationScore(Direction.EAST), path + e)
            } else if ((e to Direction.EAST) !in visited && !input.isWall(e)) {
                movementQueue += Path(e, Direction.EAST, score + 1 + direction.rotationScore(Direction.EAST), path + e)
            }

            val s = point.s
            if (s == end) {
                scores += GoodPath(score + 1 + direction.rotationScore(Direction.SOUTH), path + s)
            } else if ((s to Direction.SOUTH) !in visited && !input.isWall(s)) {
                movementQueue += Path(s, Direction.SOUTH, score + 1 + direction.rotationScore(Direction.SOUTH), path + s)
            }

            val w = point.w
            if (w == end) {
                scores += GoodPath(score + 1 + direction.rotationScore(Direction.WEST), path + w)
            } else if ((w to Direction.WEST) !in visited && !input.isWall(w)) {
                movementQueue += Path(w, Direction.WEST, score + 1 + direction.rotationScore(Direction.WEST), path + w)
            }
        }

        val lowestScore = scores.minOfOrNull { it.score } ?: Integer.MAX_VALUE

        return scores
            .filter { it.score == lowestScore }
            .flatMap { it.path }
            .toSet()
            .size
    }

    enum class Direction {
        NORTH, EAST, SOUTH, WEST;

        fun rotationScore(other: Direction): Int = when (this) {
            NORTH -> when (other) {
                NORTH -> 0
                EAST -> 1000
                SOUTH -> 2000
                WEST -> 1000
            }
            EAST -> when (other) {
                NORTH -> 1000
                EAST -> 0
                SOUTH -> 1000
                WEST -> 2000
            }
            SOUTH -> when (other) {
                NORTH -> 2000
                EAST -> 1000
                SOUTH -> 0
                WEST -> 1000
            }
            WEST -> when (other) {
                NORTH -> 1000
                EAST -> 2000
                SOUTH -> 1000
                WEST -> 0
            }
        }
    }

    private fun Map<Point, Char>.isWall(point: Point) = get(point) == '#'
}
