package y2022.day03

import java.io.File

fun main() {
    println("Part one: ${Day03.solvePartOne()}")
    println("Part two: ${Day03.solvePartTwo()}")
}

object Day03 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val input =
        File("src/main/kotlin/$directory/input.txt")
            .readLines()
            .map(String::toList)

    fun solvePartOne(): Int {
        val dupeItems = mutableListOf<Char>()
        input.forEach { rucksack ->
            val compartments = rucksack.chunked(rucksack.size / 2)
            dupeItems += compartments[0].intersect(compartments[1].toSet())
        }

        return dupeItems.calculateScore()
    }

    fun solvePartTwo(): Int {
        val badges = mutableListOf<Char>()

        input
            .chunked(3)
            .forEach { group ->
                val firstIntersect = group[0].intersect(group[1].toSet())
                val secondIntersect = group[0].intersect(group[2].toSet())
                badges += firstIntersect.intersect(secondIntersect)
            }

        return badges.calculateScore()
    }

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
