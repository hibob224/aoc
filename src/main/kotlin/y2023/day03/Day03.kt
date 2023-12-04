package y2023.day03

import utils.Point
import utils.getInputFile

fun main() {
    println("Part one: ${Day03.solvePartOne()}")
    println("Part two: ${Day03.solvePartTwo()}")
}

// Written on a plane, on mobile, in kotlin playground...
object Day03 {

    private val input = parseGrid()

    fun solvePartOne(): Long {
        val symbolCoords = input
            .filterValues { it.toLongOrNull() == null }
            .map { it.key.first() }

        return input
            .filterValues { it.toLongOrNull() != null } // Remove any symbols, only work with number values
            .filter { (coords, value) ->
                coords.any { point -> point.getNeighbours(diagonal = true).any { it in symbolCoords } }
            } // Filter to only those next to symbols
            .values
            .sumOf { it.toLong() }
    }

    fun solvePartTwo(): Long {
        val values = input
            .filterValues { it.toLongOrNull() != null }
            .map { it.key to it.value.toLong() }
            .toMap()

        return input
            .filterValues { it == "*" }
            .entries
            .fold(0) { acc, (coords, _) ->
                val neighbours = coords.first().getNeighbours(diagonal = true)
                val validNeighbours = values.filterKeys { points -> points.any { it in neighbours } }

                if (validNeighbours.size == 2) {
                    acc + validNeighbours.values.reduce { acc2, l -> acc2 * l }
                } else {
                    acc
                }
            }
    }

    private fun parseGrid(): Map<List<Point>, String> {
        return buildMap<List<Point>, String> {
            getInputFile(this@Day03::class.java.packageName, example = false)
                .readLines()
                .forEachIndexed { y, line ->
                    var acc = ""
                    line.forEachIndexed { x, c ->
                        if (c.isDigit()) {
                            acc += c
                        } else if (acc.isNotEmpty()) {
                            put(acc.indices.map { Point(x - it - 1, y) }, acc)
                            put(listOf(Point(x, y)), c.toString())
                            acc = ""
                        } else {
                            put(listOf(Point(x, y)), c.toString())
                        }
                    }
                    if (acc.isNotEmpty()) {
                        put(acc.indices.map { Point(line.length - it - 1, y) }, acc)
                    }
                    acc = ""
                }
        }.filterNot { it.value == "." }
    }
}
