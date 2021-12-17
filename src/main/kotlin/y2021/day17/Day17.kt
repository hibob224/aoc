package y2021.day17

import utils.Point
import java.io.File

fun main() {
    println("Part one: ${Day17.solvePartOne()}")
    println("Part two: ${Day17.solvePartTwo()}")
}

object Day17 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val regex = """^target area: x=(-?\d+)\.\.(-?\d+), y=(-?\d+)..(-?\d+)${'$'}""".toRegex()
    private val input =
        File("src/main/kotlin/$directory/input.txt")
            .readLines()
            .first()

    fun solvePartOne(): Int {
        val (x1, x2, y1, y2) = regex.find(input)!!.groupValues.takeLast(4).map(String::toInt)
        var highY = Integer.MIN_VALUE

        (-1000..1000).forEach { x ->
            (-1000..1000).forEach { y ->
                var vX = x
                var vY = y
                var trajectoryHigh = Integer.MIN_VALUE
                var probePos = Point(0, 0)
                var hitTargetArea = false

                while (
                    probePos.x < maxOf(x1, x2) &&
                        probePos.y > minOf(y1, y2) &&
                        !hitTargetArea
                ) {
                    probePos = probePos.copy(x = probePos.x + vX, y = probePos.y + vY)
                    vX = vX.dec().coerceAtLeast(0)
                    vY -= 1
                    trajectoryHigh = maxOf(trajectoryHigh, probePos.y)
                    hitTargetArea = probePos.x in x1..x2 && probePos.y in y1..y2
                }

                if (hitTargetArea) {
                    highY = maxOf(highY, trajectoryHigh)
                }
            }
        }

        return highY
    }

    fun solvePartTwo(): Int {
        val (x1, x2, y1, y2) = regex.find(input)!!.groupValues.takeLast(4).map(String::toInt)
        var hits = 0

        (-1000..1000).forEach { x ->
            (-1000..1000).forEach { y ->
                var vX = x
                var vY = y
                var probePos = Point(0, 0)
                var hitTargetArea = false

                while (
                    probePos.x < maxOf(x1, x2) &&
                    probePos.y > minOf(y1, y2) &&
                    !hitTargetArea
                ) {
                    probePos = probePos.copy(x = probePos.x + vX, y = probePos.y + vY)
                    vX = vX.dec().coerceAtLeast(0)
                    vY -= 1
                    hitTargetArea = probePos.x in x1..x2 && probePos.y in y1..y2
                }

                if (hitTargetArea) {
                    hits++
                }
            }
        }

        return hits
    }
}