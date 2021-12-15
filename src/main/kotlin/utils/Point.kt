package utils

import kotlin.math.abs
import kotlin.math.atan2

data class Point(val x: Int, val y: Int) {

    companion object {
        private val neighbours =
            mutableListOf(Point(x = 0, y = 1), Point(x = 0, y = -1), Point(x = 1, y = 0), Point(x = -1, y = 0))
        private val diagonalNeighbours =
            mutableListOf(Point(x = 1, y = 1), Point(x = -1, y = -1), Point(x = 1, y = -1), Point(x = -1, y = 1))
    }

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

    fun getNeighbours(diagonal: Boolean = false): List<Point> = if (diagonal) {
        neighbours + diagonalNeighbours
    } else {
        neighbours
    }.map {
        copy(x = x + it.x, y = y + it.y)
    }

    override fun toString(): String = "($x, $y)"
}