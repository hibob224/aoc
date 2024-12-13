package y2015.day03

import utils.Point
import utils.getInputFile

fun main() {
    println("Part one: " + Day03.solvePartOne())
    println("Part two: " + Day03.solvePartTwo())
}

object Day03 {

    private val input = getInputFile(this::class.java.packageName).readText()

    fun solvePartOne(): Int = followRoute(input.toList()).toSet().size

    fun solvePartTwo(): Int {
        val (santa, robo) = input
            .toList()
            .chunked(2, List<Char>::zipWithNext)
            .flatten()
            .unzip()

        return (followRoute(santa) + followRoute(robo)).toSet().size
    }

    private fun followRoute(route: List<Char>): List<Point> = route.fold(listOf(Point(0, 0))) { acc, c ->
        acc + when (c) {
            '>' -> acc.last().e
            'v' -> acc.last().s
            '^' -> acc.last().n
            '<' -> acc.last().w
            else -> error("Where?")
        }
    }
}
