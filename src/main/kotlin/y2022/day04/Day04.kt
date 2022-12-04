package y2022.day04

import java.io.File

fun main() {
    println("Part one: ${Day04.solvePartOne()}")
    println("Part two: ${Day04.solvePartTwo()}")
}

object Day04 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val regex = """^(\d+)-(\d+),(\d+)-(\d+)$""".toRegex()
    private val input =
        File("src/main/kotlin/$directory/input.txt")
            .readLines()
            .map {
                val (_, oneStart, oneEnd, twoStart, twoEnd) = regex.find(it)!!.groupValues
                Assignments(oneStart.toInt()..oneEnd.toInt(), twoStart.toInt()..twoEnd.toInt())
            }

    fun solvePartOne(): Int = input.count { it.elfOne.contains(it.elfTwo) || it.elfTwo.contains(it.elfOne) }

    fun solvePartTwo(): Int = input.count { it.elfOne.overlap(it.elfTwo) || it.elfTwo.overlap(it.elfOne) }

    data class Assignments(
        val elfOne: IntRange,
        val elfTwo: IntRange,
    )

    /**
     * True if this range fully contains the [other]
     */
    private fun IntRange.contains(other: IntRange): Boolean = first <= other.first && last >= other.last

    /**
     * True if there is any overlap between this range and the [other]
     */
    private fun IntRange.overlap(other: IntRange): Boolean = (other.first in first..last) || (other.last in first..last)
}
