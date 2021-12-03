package y2021.day03

import utils.orZero
import java.io.File

fun main() {
    println("Part one: ${Day03.solvePartOne()}")
    println("Part two: ${Day03.solvePartTwo()}")
}

object Day03 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val fileInput = File("src/main/kotlin/$directory/input.txt").readLines()

    fun solvePartOne(): Int {
        val input = fileInput
            .map { it.toCharArray().toList() }
            .transpose()
            .map { it.groupingBy { it }.eachCount().maxByOrNull { it.value }!!.key.toString().toInt() }

        val gamma = input.joinToString(separator = "") { "$it" }
        val epsilon = input.map { it xor 1 }.joinToString(separator = "") { "$it" }

        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun solvePartTwo(): Int {
        val oxygenRatingCandidates = fileInput.toMutableList()
        val co2RatingCandidates = fileInput.toMutableList()

        var index = 0

        while (oxygenRatingCandidates.size > 1) {
            val counts = oxygenRatingCandidates
                .map { it[index] }
                .groupingBy { it }
                .eachCount()
            val mostCommonAtIndex = '1'.takeIf { counts['1'].orZero() >= counts['0'].orZero() } ?: '0'
            oxygenRatingCandidates.removeIf { it[index] != mostCommonAtIndex }
            index++
        }

        index = 0

        while (co2RatingCandidates.size > 1) {
            val counts = co2RatingCandidates
                .map { it[index] }
                .groupingBy { it }
                .eachCount()
            val leastCommonAtIndex = '0'.takeIf { counts['0'].orZero() <= counts['1'].orZero() } ?: '1'
            co2RatingCandidates.removeIf { it[index] != leastCommonAtIndex }
            index++
        }

        val oxyRating = oxygenRatingCandidates.first().toInt(2)
        val co2Rating = co2RatingCandidates.first().toInt(2)

        return oxyRating * co2Rating
    }

    private fun <T> List<List<T>>.transpose(): List<List<T>> {
        val result = (first().indices).map { mutableListOf<T>() }.toMutableList()
        forEach { list -> result.zip(list).forEach { it.first.add(it.second) } }
        return result
    }
}