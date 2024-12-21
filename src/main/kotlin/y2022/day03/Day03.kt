package y2022.day03

import utils.getInputFile

fun main() {
    println("Part one: ${Day03.solvePartOne()}")
    println("Part two: ${Day03.solvePartTwo()}")
}

object Day03 {

    private val input = getInputFile()
        .readLines()
        .map(String::toList)

    fun solvePartOne(): Int = input.flatMap { rucksack ->
        val compartments = rucksack.chunked(rucksack.size / 2)
        compartments[0].intersect(compartments[1].toSet())
    }.calculateScore()

    fun solvePartTwo(): Int = input
        .chunked(3)
        .flatMap { group ->
            val firstIntersect = group[0].intersect(group[1].toSet())
            val secondIntersect = group[0].intersect(group[2].toSet())
            firstIntersect.intersect(secondIntersect)
        }.calculateScore()

    /**
     * Calculate score based on a-z = 1-26 and A-Z = 27-52
     */
    private fun List<Char>.calculateScore(): Int = sumOf {
        // Take the ASCII code of each char, and offset it so that we start at 1 or 27 based on casing
        if (it.isUpperCase()) {
            it.code - 38
        } else {
            it.code - 96
        }
    }
}
