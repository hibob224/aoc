package y2015.day04

import utils.getInputFile
import utils.md5

fun main() {
    println("Part one: " + Day04.solvePartOne())
    println("Part two: " + Day04.solvePartTwo())
}

object Day04 {

    private val input = getInputFile(this::class.java.packageName).readText()

    fun solvePartOne(): Int = findPrefix("00000")

    fun solvePartTwo(): Int = findPrefix("000000")

    private fun findPrefix(prefix: String): Int =
        generateSequence(0, Int::inc)
            .find { input.plus(it).md5.startsWith(prefix) }!!
}
