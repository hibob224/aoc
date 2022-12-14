package y2022.day08

import utils.Point
import utils.getInputFile

fun main() {
    println("Part one: ${Day08.solvePartOne()}")
    println("Part two: ${Day08.solvePartTwo()}")
}

object Day08 {

    private val input = getInputFile(this::class.java.packageName)
        .readLines()
        .also {
            height = it.lastIndex
            width = it.first().lastIndex
        }
        .flatMapIndexed { y, s ->
            s.mapIndexed { x, c ->
                Point(x, y) to c.digitToInt()
            }
        }.toMap()
    private var width = 0
    private var height = 0

    fun solvePartOne(): Int {
        var visibleCount = 0

        input.forEach { (point, pointHeight) ->
            if (point.x == 0 || point.x == width || point.y == 0 || point.y == height) {
                visibleCount++
                return@forEach
            }

            val visibleLeft = (0..point.x.dec()).mapNotNull { input[Point(it, point.y)] }.all { it < pointHeight }
            val visibleRight = (point.x.inc()..width).mapNotNull { input[Point(it, point.y)] }.all { it < pointHeight }
            val visibleUp = (0..point.y.dec()).mapNotNull { input[Point(point.x, it)] }.all { it < pointHeight }
            val visibleDown = (point.y.inc()..height).mapNotNull { input[Point(point.x, it)] }.all { it < pointHeight }
            if (visibleUp || visibleDown || visibleLeft || visibleRight) {
                visibleCount++
            }
        }


        return visibleCount
    }

    fun solvePartTwo(): Int {
        val scenicScores = mutableListOf<Int>()
        input.forEach { (point, pointHeight) ->
            var visibleLeft = 0
            if (point.x > 0) {
                val trees = (0..point.x.dec()).mapNotNull { input[Point(it, point.y)] }.reversed()
                visibleLeft = trees.indexOfFirst { it >= pointHeight }.takeIf { it > -1 }?.inc() ?: trees.size
            }
            var visibleRight = 0
            if (point.x < width) {
                val trees = (point.x.inc()..width).mapNotNull { input[Point(it, point.y)] }
                visibleRight = trees.indexOfFirst { it >= pointHeight }.takeIf { it > -1 }?.inc() ?: trees.size
            }
            var visibleUp = 0
            if (point.y > 0) {
                val trees = (0..point.y.dec()).mapNotNull { input[Point(point.x, it)] }.reversed()
                visibleUp = trees.indexOfFirst { it >= pointHeight }.takeIf { it > -1 }?.inc() ?: trees.size
            }
            var visibleDown = 0
            if (point.x < width) {
                val trees = (point.y.inc()..height).mapNotNull { input[Point(point.x, it)] }
                visibleDown = trees.indexOfFirst { it >= pointHeight }.takeIf { it > -1 }?.inc() ?: trees.size
            }
            scenicScores += visibleLeft * visibleRight * visibleUp * visibleDown
        }
        return scenicScores.max()
    }
}
