package utils

import kotlin.math.abs

data class Point3D(val x: Int, val y: Int, val z: Int) {

    fun manhattan(other: Point3D) = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)

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
