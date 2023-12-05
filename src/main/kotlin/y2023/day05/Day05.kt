package y2023.day05

import utils.getInputFile

fun main() {
    println("Part one: ${Day05.solvePartOne()}")
    println("Part two: ${Day05.solvePartTwo()}")
}

object Day05 {

    private val seeds: List<Long>
    private val mappings: List<Map<LongRange, LongRange>>

    init {
        val input = getInputFile(this::class.java.packageName, example = false).readLines()
        seeds = input[0].split(": ")[1].split(" ").map(String::toLong)

        mappings = input
            .drop(n = 2)
            .joinToString("\n")
            .split("\n\n")
            .map {
                it.lines()
                    .drop(n = 1)
                    .associate {
                        val (dest, source, length) = it
                            .split(" ")
                            .map { it.toLong() }
                        source..(source + length) to dest..(dest + length)
                    }
            }
    }

    fun solvePartOne(): Long {
        return seeds
            .minOf {
                mappings.fold(it) { acc, mapping ->
                    mapping.entries
                        .firstOrNull { acc in it.key }
                        ?.let { (source, dest) ->
                            dest.first + (acc - source.first)
                        } ?: acc
                }
            }
    }

    fun solvePartTwo(): Long = 0


}
