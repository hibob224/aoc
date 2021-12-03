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
        val threshold = fileInput.size / 2
        val gamma = List(fileInput.first().length) { index ->
            if (fileInput.count { it[index] == '1' } >= threshold) 1 else 0
        }
        val epsilon = gamma.map { it xor 1 }

        return gamma.joinToString(separator = "") { "$it" }.toInt(2) * epsilon.joinToString(separator = "") { "$it" }.toInt(2)
    }

    fun solvePartTwo(): Int {
        var oxygenRatingCandidates = fileInput.toList()
        var co2RatingCandidates = fileInput.toList()

        (0 until fileInput.first().length).forEach { index ->
            oxygenRatingCandidates = filterByMostCommon(oxygenRatingCandidates, index)
            co2RatingCandidates = filterByLeastCommon(co2RatingCandidates, index)
        }

        val oxyRating = oxygenRatingCandidates.first().toInt(2)
        val co2Rating = co2RatingCandidates.first().toInt(2)

        return oxyRating * co2Rating
    }

    private fun filterByMostCommon(list: List<String>, index: Int): List<String> {
        if (list.size == 1) return list
        val counts = list
            .map { it[index] }
            .groupingBy { it }
            .eachCount()
        val mostCommonAtIndex = '1'.takeIf { counts['1'].orZero() >= counts['0'].orZero() } ?: '0'
        return list.filter { it[index] == mostCommonAtIndex }
    }

    private fun filterByLeastCommon(list: List<String>, index: Int): List<String> {
        if (list.size == 1) return list
        val counts = list
            .map { it[index] }
            .groupingBy { it }
            .eachCount()
        val leastCommonAtIndex = '0'.takeIf { counts['0'].orZero() <= counts['1'].orZero() } ?: '1'
        return list.filter { it[index] == leastCommonAtIndex }
    }
}