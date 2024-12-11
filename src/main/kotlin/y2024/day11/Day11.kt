package y2024.day11

import utils.getInputFile
import utils.isEven

fun main() {
    println("Part one: " + Day11.solvePartOne())
    println("Part two: " + Day11.solvePartTwo())
}

object Day11 {

    private val input = getInputFile(this::class.java.packageName, example = false)
        .readText()
        .split(' ')
        .map { it.toLong() }
        .groupingBy { it }
        .eachCount()
        .mapValues { it.value.toLong() } // StoneValue to Count

    fun solvePartOne(): Long = solve(25, input)

    fun solvePartTwo(): Long = solve(75, input)

    private fun solve(blinks: Int, stones: Map<Long, Long>) =
        (0 until blinks).fold(stones) { stones, _ ->
            stones
                .flatMap { (stone, count) ->
                    blink(stone).map { transform -> transform to count } // Blink, then map to StoneValue to count (using original stones count)
                }
                .groupingBy { it.first } // Group up by stone values
                .fold(0L) { acc, (_, count) -> acc + count } // Sum up each stones count, resulting in StoneValue to count
                .toMutableMap()
        }.values.sum() // Sum up how many stones we have

    private fun blink(it: Long) = when {
        it == 0L -> listOf(1L)
        it.toString().length.isEven -> listOf(
            it.toString().substring(0, it.toString().length / 2).toLong(),
            it.toString().substring(it.toString().length / 2).toLong(),
        )
        else -> listOf(it * 2024)
    }
}
