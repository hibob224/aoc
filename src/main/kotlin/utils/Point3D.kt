package utils

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class Point3D(val x: Int, val y: Int, val z: Int) {

    constructor(coords: Triple<Int, Int, Int>) : this(coords.first, coords.second, coords.third)
    constructor(coords: List<Int>) : this(coords[0], coords[1], coords[2])

    fun manhattan(other: Point3D) = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)

    fun euclidean(other: Point3D): Double = sqrt((other.x - x).toDouble().pow(2) + (other.y - y).toDouble().pow(2) + (other.z - z).toDouble().pow(2))

    fun getNeighbours(): List<Point3D> = directNeighbourTranslations.map { it + this }

    operator fun plus(other: Point3D) = copy(x = x + other.x, y = y + other.y, z = z + other.z)

    override fun toString(): String = "($x, $y, $z)"

    private companion object {
        val directNeighbourTranslations = listOf(
            Point3D(-1, 0, 0),
            Point3D(1, 0, 0),
            Point3D(0, -1, 0),
            Point3D(0, 1, 0),
            Point3D(0, 0, -1),
            Point3D(0, 0, 1)
        )
    }
}
