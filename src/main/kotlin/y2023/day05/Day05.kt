package y2023.day05

import kotlinx.coroutines.*
import utils.getInputFile

fun main() {
    println("Part one: ${Day05.solvePartOne()}")
    runBlocking { println("Part two: ${Day05.solvePartTwo()}") }
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
            .map { groups ->
                groups.lines()
                    .drop(n = 1)
                    .associate {
                        val (dest, source, length) = it
                            .split(" ")
                            .map(String::toLong)
                        @OptIn(ExperimentalStdlibApi::class)
                        source..<(source + length) to dest..<(dest + length)
                    }
            }
    }

    fun solvePartOne(): Long = seeds.minOf(::findLocation)

    @OptIn(ExperimentalStdlibApi::class)
    suspend fun solvePartTwo(): Long = withContext(Dispatchers.IO) {
        return@withContext seeds
            .chunked(2)
            .map { it.first()..<it.first() + it[1] }
            .map { range ->
                async {
                    range.minOf(::findLocation)
                }
            }
            .awaitAll()
            .min()
    }

    private fun findLocation(seed: Long): Long =
        mappings.fold(seed) { acc, mapping ->
            mapping.entries
                .firstOrNull { acc in it.key }
                ?.let { (source, dest) ->
                    dest.first + (acc - source.first)
                } ?: acc
        }
}
