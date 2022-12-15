package y2022.day15

import utils.Point
import utils.getInputFile
import kotlin.math.absoluteValue

fun main() {
    println("Part one: ${Day15.solvePartOne()}")
    println("Part two: ${Day15.solvePartTwo()}")
}

object Day15 {

    private val regex = """^Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)$""".toRegex()
    private val input = getInputFile(this::class.java.packageName)
        .readLines()
        .map {
            val (_, x1, y1, x2, y2) = regex.find(it)!!.groupValues
            Sensor(Point(x1.toInt(), y1.toInt()), Point(x2.toInt(), y2.toInt()))
        }

    fun solvePartOne(): Int {
        val targetY = 2_000_000
        val beacons = input.map(Sensor::closestBeacon)

        return input
            .filter { (it.position.y - targetY).absoluteValue < it.beaconDistance }
            .fold(setOf<Point>()) { acc, sensor ->
                val distanceToTargetY = sensor.beaconDistance - sensor.position.manhattan(sensor.position.copy(y = targetY))
                acc + (-distanceToTargetY..distanceToTargetY)
                    .map { x -> Point(sensor.position.x + x, targetY) }
                    .filter { it !in beacons }
            }.size
    }

    fun solvePartTwo(): Long {
        val tuningMultiplier = 4_000_000
        val maxCoord = 4_000_000

        var y = 0
        var x: Int

        while (y <= maxCoord) {
            x = 0
            while (x <= maxCoord) {
                val point = Point(x, y)
                val sensor = input.find { it.position.manhattan(point) <= it.beaconDistance } ?: return point.x.toLong() * tuningMultiplier + point.y
                val diff = sensor.beaconDistance - sensor.position.manhattan(point)
                x += maxOf(diff, 1) // Increment by at least one, because I get stuck at some point with a 0 diff, unsure why
            }
            y++
        }
        error("Fail")
    }

    data class Sensor(val position: Point, val closestBeacon: Point) {
        val beaconDistance = position.manhattan(closestBeacon)
    }
}
