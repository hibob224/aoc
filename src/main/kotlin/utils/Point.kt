package utils

import kotlin.math.abs
import kotlin.math.atan2

open class Point(val x: Int, val y: Int) {

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

    open fun copy(x: Int = this.x, y: Int = this.y) = Point(x, y)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}