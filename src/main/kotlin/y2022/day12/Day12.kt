package y2022.day12

import utils.Point
import utils.getInputFile

fun main() {
    println("Part one: ${Day12.solvePartOne()}")
    println("Part two: ${Day12.solvePartTwo()}")
}

object Day12 {

    private lateinit var start: Point
    private lateinit var target: Point
    private val input = getInputFile(this::class.java.packageName)
        .readLines()
        .flatMapIndexed { y: Int, row: String ->
            row.mapIndexed { x, level ->
                val point = Point(x, y)
                val mappedLevel = when (level) {
                    'S' -> {
                        start = point
                        'a'
                    }

                    'E' -> {
                        target = point
                        'z'
                    }

                    else -> level
                }
                point to mappedLevel
            }
        }
        .toMap()

    fun solvePartOne(): Int = solve(start)

    // Bored, let's just run solve on all possible starts, all 1000 of 'em
    fun solvePartTwo(): Int = input.filterValues { it == 'a' }.minOf { solve(it.key) }

    // A-Star
    private fun solve(start: Point): Int {
        val openSet = mutableSetOf(start)
        val cameFrom = mutableMapOf<Point, Point>()
        val gScore = mutableMapOf(start to 0)
        val fScore = mutableMapOf(start to Integer.MAX_VALUE)

        while (openSet.isNotEmpty()) {
            val current = openSet.minByOrNull { fScore[it]!! }!!
            if (current == target) {
                val path = mutableListOf(current)
                var next = current
                while (next in cameFrom.keys) {
                    next = cameFrom[next]!!
                    path.add(0, next)
                }
                return path.size.dec()
            }

            openSet.remove(current)
            val currentHeight = input[current]!!
            current.getNeighbours()
                .filter { it in input.keys }
                .filter { input[it]!! - currentHeight <= 1 }
                .forEach { neighbour ->
                    val tentativeGScore = gScore[current]!! + 1
                    if (tentativeGScore < gScore.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                        cameFrom[neighbour] = current
                        gScore[neighbour] = tentativeGScore
                        fScore[neighbour] = tentativeGScore + neighbour.manhattan(target)
                        if (neighbour !in openSet) {
                            openSet += neighbour
                        }
                    }
                }

        }
        return Integer.MAX_VALUE
    }
}
