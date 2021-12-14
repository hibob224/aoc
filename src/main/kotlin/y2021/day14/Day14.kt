package y2021.day14

import java.io.File

fun main() {
    println("Part one: ${Day14.solvePartOne()}")
    println("Part two: ${Day14.solvePartTwo()}")
}

object Day14 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val regex = """^([A-Z]{2}) -> ([A-Z])${'$'}""".toRegex()
    private val input: List<String> = File("src/main/kotlin/$directory/input.txt").readLines()
    private val counts: Map<String, Long> = input
        .first()
        .windowed(2)
        .groupingBy { it }
        .eachCount()
        .map { it.key to it.value.toLong() }
        .toMap()
    private val rules: Map<String, List<String>> =
        (2..input.lastIndex).associate { index ->
            val (_, a, b) = regex.find(input[index])!!.groupValues
            a to listOf("${a.first()}$b", "$b${a[1]}")
        }

    fun solvePartOne(): Long = solve(10)

    fun solvePartTwo(): Long = solve(40)

    private fun solve(iterations: Int): Long {
        var counts = this.counts
        repeat(iterations) {
            val newCounts = mutableMapOf<String, Long>()
            counts.forEach { (key, value) ->
                rules[key]?.let { rule ->
                    rule.forEach {
                        newCounts.putIfAbsent(it, 0L)
                        newCounts[it] = newCounts[it]!! + value
                    }
                } ?: run {
                    newCounts.putIfAbsent(key, 0L)
                    newCounts[key] = newCounts[key]!! + value
                }
            }
            counts = newCounts
        }

        val letterCount = mutableMapOf<Char, Long>()

        counts.forEach { (key, value) ->
            key.toCharArray().forEach {
                letterCount.putIfAbsent(it, 0L)
                letterCount[it] = letterCount[it]!! + value
            }
        }

        return ((letterCount.maxByOrNull { it.value }!!.value - letterCount.minByOrNull { it.value }!!.value) / 2)
    }
}