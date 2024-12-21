package y2024.day08

import utils.Point
import utils.getInputFile
import utils.toPointGrid

fun main() {
    println("Part one: " + Day08.solvePartOne())
    println("Part two: " + Day08.solvePartTwo())
}

object Day08 {

    private val input =
        getInputFile()
            .readLines()
            .toPointGrid()
    private val width = input.maxOf { it.key.x }
    private val height = input.maxOf { it.key.y }
    private val antennas = input.filterValues { it != '.' }

    fun solvePartOne(): Int {
        return buildSet {
            antennas
                .forEach { (point, value) ->
                    point
                        .pairedAntenna(value)
                        .forEach { match ->
                            val diff = point - match
                            listOf(point - diff, match - diff)
                                .filterNot { point == it || match == it }
                                .filter { it.inBounds(width, height) }
                                .let(::addAll)
                        }
                }
        }.size
    }

    fun solvePartTwo(): Int {
        return buildSet {
            antennas
                .forEach { (point, value) ->
                    point
                        .pairedAntenna(value)
                        .forEach { match ->
                            val diff = point - match
                            val posSeq = generateSequence(point) { prev -> (prev + diff).takeIf { it.inBounds(width, height) } }
                            val negSeq = generateSequence(point) { prev -> (prev - diff).takeIf { it.inBounds(width, height) } }
                            (posSeq + negSeq).let(::addAll)
                        }
                }
        }.size
    }

    private fun Point.pairedAntenna(value: Char): Collection<Point> =
        antennas
            .filterValues { it == value }
            .filterKeys { it != this }
            .keys

    private fun Point.inBounds(width: Int, height: Int): Boolean = x in 0..width && y in 0..height
}

