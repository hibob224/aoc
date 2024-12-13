package y2015.day06

import utils.Point
import utils.getInputFile
import utils.pointsInArea

fun main() {
    println("Part one: " + Day06.solvePartOne())
    println("Part two: " + Day06.solvePartTwo())
}

object Day06 {

    private val regex = """^(turn on|turn off|toggle) (\d+),(\d+) through (\d+),(\d+)$""".toRegex()
    private val input = getInputFile(this::class.java.packageName)
        .readLines()
        .map {
            val (operation, aX, aY, bX, bY) = regex.find(it)!!.destructured
            Triple(Operation.fromValue(operation), Point(aX.toInt(), aY.toInt()), Point(bX.toInt(), bY.toInt()))
        }

    fun solvePartOne(): Int {
        val lights = mutableMapOf<Point, Boolean>()

        input.forEach { (operation, tl, br) ->
            pointsInArea(tl, br).forEach { point ->
                lights[point] = when (operation) {
                    Operation.ON -> true
                    Operation.OFF -> false
                    Operation.TOGGLE -> !lights.getOrDefault(point, false)
                }
            }
        }

        return lights.values.count { it }
    }

    fun solvePartTwo(): Int {
        val lights = mutableMapOf<Point, Int>()

        input.forEach { (operation, tl, br) ->
            pointsInArea(tl, br).forEach { point ->
                lights[point] = (lights.getOrDefault(point, 0) + when (operation) {
                    Operation.ON -> 1
                    Operation.OFF -> -1
                    Operation.TOGGLE -> 2
                }).coerceAtLeast(0)
            }
        }

        return lights.values.sum()
    }

    enum class Operation {
        ON, OFF, TOGGLE;

        companion object {
            fun fromValue(value: String) = when (value) {
                "turn on" -> ON
                "turn off" -> OFF
                "toggle" -> TOGGLE
                else -> throw IllegalArgumentException("Unknown operation $value")
            }
        }
    }
}
