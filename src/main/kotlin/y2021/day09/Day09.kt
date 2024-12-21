package y2021.day09

import utils.Point
import utils.getInputFile

fun main() {
    println("Part one: ${Day09.solvePartOne()}")
    println("Part two: ${Day09.solvePartTwo()}")
}

object Day09 {

    private val input: MutableMap<Point, Int> =
        getInputFile()
            .readLines()
            .flatMapIndexed { y, row ->
                row.mapIndexed { x, c ->
                    Point(x, y) to c.toString().toInt()
                }
            }.toMap().toMutableMap()

    fun solvePartOne(): Int = input
        .filter {
            val height = it.value
            it.key.getNeighbours().all { point ->
                height < input.getOrDefault(point, Integer.MAX_VALUE)
            }
        }.let {
            it.values.sum() + it.size
        }

    fun solvePartTwo(): Int {
        // Get the low points
        val lowPoints = input.filter {
            val height = it.value
            it.key.getNeighbours().all { point ->
                height < input.getOrDefault(point, Integer.MAX_VALUE)
            }
        }.keys

        return lowPoints.map {
            val basinArea = mutableSetOf<Point>()
            val locations = ArrayDeque(listOf(it))

            while (locations.isNotEmpty()) {
                val point = locations.removeFirst()

                // Go through every move from this point, if we haven't seen the new point before and it isn't a ridge
                // (value 9), then add it to the basinArea and list of locations to check
                point.getNeighbours().forEach { neighbour ->
                    if (neighbour !in basinArea && input.getOrDefault(neighbour, 9) != 9) {
                        // Valid space, expand
                        basinArea += neighbour
                        locations.addFirst(neighbour)
                    }
                }
            }
            basinArea.size
        }.sortedDescending().take(3).reduce { acc, i -> acc * i }
    }
}