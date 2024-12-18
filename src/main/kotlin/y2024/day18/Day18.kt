package y2024.day18

import utils.Point
import utils.getInputFile
import utils.shortestPath

fun main() {
    println("Part one: " + Day18.solvePartOne())
    println("Part two: " + Day18.solvePartTwo())
}

object Day18 {

    private val example = false
    private val p1GridSize = if (example) 6 else 70
    private val p1Bytes = if (example) 12 else 1024

    private fun parseInput(bytes: Int) = getInputFile(this::class.java.packageName, example = example)
        .readLines()
        .take(bytes)
        .associate {
            val (x, y) = it.split(",").map(String::toInt)
            Point(x, y) to '#'
        }


    fun solvePartOne(): Int {
        val grid = parseInput(p1Bytes)

//        grid.printGrid()

        return grid.shortestPath(
            start = Point(0, 0),
            target = Point(p1GridSize, p1GridSize),
        ) { _, new, _ ->
            if (new in grid) -1 else 1
        }.also {
//            println(it.first)
        }.second
    }

    fun solvePartTwo(): Long {
        return 0
    }
}
