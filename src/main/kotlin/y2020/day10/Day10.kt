package y2020.day10

import utils.getInputFile
import utils.orZero

fun main() {
    println("Part one: ${Day10.solvePartOne()}")
    println("Part two: ${Day10.solvePartTwo()}")
}

object Day10 {

    private val input: List<Int> =
        getInputFile()
            .readLines()
            .map(String::toInt)
            .sorted()
            .toMutableList().also {
                it.add(it.last() + 3)
            }

    fun solvePartOne(): String {
        return input
            .toMutableList()
            .also { it.add(0, 0) }
            .windowed(2)
            .map { it.last() - it.first() }
            .groupBy { it }
            .let { it[1]?.size.orZero() * it[3]?.size.orZero() }
            .toString()
    }

    fun solvePartTwo(): String {
        return input.fold(mapOf(0 to 1L)) { acc, i ->
            // Map of adapter jolt value to number of possible combinations that can reach this adapter (sum of number of routes to the previous 3 jolt values)
            acc.plus(i to (i - 3 until i).map { acc[it].orZero() }.sum())
        }[input.last()].toString() // Value of the last adapter will be the total number of routes
    }
}