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
    private val cache = mutableMapOf<Long, List<Long>>()

    fun solvePartOne(): Int {
        var blinks: List<String> = input.toMutableList()
        repeat(25) {
            blinks = blink(blinks)
        }
        return blinks.size
    }

    fun solvePartTwo(): Long {
        val groupedInput = input
            .map { it.toLong() }
            .groupingBy { it }
            .eachCount()
            .mapValues { it.value.toLong() }

        return (0 until 75).fold(groupedInput) { stones, _ ->
            stones
                .flatMap { (stone, count) ->
                    blink(stone).map { transform -> transform to count }
                }
                .groupingBy { it.first }
                .fold(0L) { acc, (_, count) -> acc + count }
                .toMutableMap()
        }.values.sum()
    }

    private fun blink(stone: Long) = cache.computeIfAbsent(stone) {
        when {
            it == 0L -> listOf(1L)
            it.toString().length.isEven -> listOf(
                it.toString().substring(0, it.toString().length / 2).toLong(),
                it.toString().substring(it.toString().length / 2).toLong(),
            )

            else -> listOf(it * 2024)
        }
    }

    private fun blink(stone: String) = buildList {
        when {
            stone == "0" -> add("1")
            stone.length.isEven -> {
                val split = listOf(
                    stone.substring(0, stone.length / 2),
                    stone.substring(stone.length / 2).toLong().toString(),
                )
                addAll(split)
            }

            else -> add(stone.toLong().times(2024).toString())
        }
    }

    private fun blink(input: List<String>) = buildList {
        input.forEach { stone ->
//                println("Handling $stone")
            when {
                stone == "0" -> {
//                        println("0 -> 1")
                    add("1")
                }

                stone.length.isEven -> {
                    val split = listOf(
                        stone.substring(0, stone.length / 2),
                        stone.substring(stone.length / 2).toLong().toString(),
                    )
//                        println(split)
                    addAll(split)
                }

                else -> add(stone.toLong().times(2024).toString())
            }
        }
    }
}
