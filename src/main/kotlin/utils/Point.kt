package utils

import java.util.*
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

data class Point(val x: Int, val y: Int) {

    companion object {
        val neighbourDirections =
            mutableListOf(Point(x = 0, y = 1), Point(x = 0, y = -1), Point(x = 1, y = 0), Point(x = -1, y = 0))
        val diagonalNeighbourDirections =
            mutableListOf(Point(x = 1, y = 1), Point(x = -1, y = -1), Point(x = 1, y = -1), Point(x = -1, y = 1))
    }

    val n: Point
        get() = this + Point(0, -1)
    val e: Point
        get() = this + Point(1, 0)
    val s: Point
        get() = this + Point(0, 1)
    val w: Point
        get() = this + Point(-1, 0)

    fun angle(other: Point) = atan2((other.x - x).toDouble(), (other.y - y).toDouble())

    fun degrees(other: Point): Double {
        var x = Math.toDegrees(angle(other))
        if (x < 0.0) {
            x += 360
        }
        if (x == 0.0) {
            return 0.0
        }
        return 360 - x
    }

    fun manhattan(other: Point): Int = abs(x - other.x) + abs(y - other.y)

    fun euclidean(other: Point): Double = sqrt((other.x - x).toDouble().pow(2) + (other.y - y).toDouble().pow(2))

    fun getNeighbours(diagonal: Boolean = false): List<Point> = if (diagonal) {
        neighbourDirections + diagonalNeighbourDirections
    } else {
        neighbourDirections
    }.map {
        copy(x = x + it.x, y = y + it.y)
    }

    fun getNeighbour(direction: Char): Point = when (direction) {
        '^' -> n
        '>' -> e
        'v' -> s
        '<' -> w
        else -> error("Unknown direction: $direction")
    }

    operator fun plus(other: Point) = Point(other.x + x, other.y + y)

    operator fun minus(other: Point) = Point(x - other.x, y - other.y)

    override fun toString(): String = "($x, $y)"
}

//region A*
/**
 * Calculate shortest path from [start] to [end] using A*. [Score] should return a cost for moving to the new position. Return -1 if the new
 * spot is invalid (i.e. a 'wall')
 */
fun <T> Map<Point, T>.shortestPath(
    start: Point,
    target: Point,
    gridWidthRange: IntRange = 0..maxOf(start.x, target.x),
    gridHeightRange: IntRange = 0..maxOf(start.y, target.y),
    score: (currentPos: Point, newPos: Point, newTerrain: T?) -> Int,
): Pair<List<Point>, Int> {
    val open = mutableSetOf(start)
    val closed = mutableSetOf<Point>()
    val cameFrom = mutableMapOf<Point, Point>()
    val gScore = mutableMapOf(start to 0)
    val fScore = mutableMapOf(start to heuristicDistance(start, target)).withDefault { Int.MAX_VALUE }

    while (open.isNotEmpty()) {
        val currentPos = open.minBy { fScore.getValue(it) }

        if (currentPos == target) {
            val path = generatePath(currentPos, cameFrom)
            return Pair(path, fScore.getValue(currentPos))
        }

        open.remove(currentPos)
        closed += currentPos

        currentPos.getNeighbours()
            .filterNot { it in closed }
            .filter { it.x in gridWidthRange && it.y in gridHeightRange }
            .forEach { neighbour ->
                val movementScore = score(currentPos, neighbour, get(neighbour))
                if (movementScore == -1) return@forEach
                val score = gScore.getValue(currentPos) + movementScore
                if (score < gScore.getOrDefault(neighbour, Int.MAX_VALUE)) {
                    if (!open.contains(neighbour)) {
                        open.add(neighbour)
                    }
                    cameFrom[neighbour] = currentPos
                    gScore[neighbour] = score
                    fScore[neighbour] = score + heuristicDistance(neighbour, target)
                }
            }
    }

    error("Couldn't find a path")
}

private fun generatePath(currentPos: Point, cameFrom: Map<Point, Point>): List<Point> = buildList {
    var current = currentPos
    while (cameFrom.containsKey(current)) {
        current = cameFrom.getValue(current)
        add(0, current)
    }
}

private fun heuristicDistance(start: Point, finish: Point): Int {
    val dx = abs(start.x - finish.x)
    val dy = abs(start.y - finish.y)
    return (dx + dy) + (-2) * minOf(dx, dy)
}
//endregion

fun <T> List<String>.toPointGrid(mapper: (Point, Char) -> T): Map<Point, T> =
    flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            val pos = Point(x, y)
            pos to mapper(pos, c)
        }
    }.toMap()

fun List<String>.toPointGrid(): Map<Point, Char> = toPointGrid { _, c -> c }

fun <T> Map<Point, T>.traverse(block: (Point, T?) -> Unit) {
    val width = maxOf { it.key.x }
    val height = maxOf { it.key.y }

    repeat(width.inc()) { x ->
        repeat(height.inc()) { y ->
            val point = Point(x, y)
            block(point, get(point))
        }
    }
}

fun Map<Point, Char>.printGrid(default: Char = '.') {
    val width = maxOf { it.key.x }
    val height = maxOf { it.key.y }

    repeat(height.inc()) { y ->
        repeat(width.inc()) { x ->
            print(getOrDefault(Point(x, y), default))
        }
        println()
    }
}

fun pointsInArea(point1: Point, point2: Point): List<Point> {
    val (minY, maxY) = listOf(point1.y, point2.y).sorted()
    val (minX, maxX) = listOf(point1.x, point2.x).sorted()

    return buildList {
        (minY..maxY).forEach { y ->
            (minX..maxX).forEach { x ->
                add(Point(x, y))
            }
        }
    }
}
