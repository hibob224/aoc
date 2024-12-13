package y2015.day05

import utils.getInputFile

fun main() {
    println("Part one: " + Day05.solvePartOne())
    println("Part two: " + Day05.solvePartTwo())
}

object Day05 {

    private val input = getInputFile(this::class.java.packageName).readLines()

    fun solvePartOne(): Int {
        val rule1 = """^(.*[aeiou].*){3}$""".toRegex()
        val rule2 = """^.*(.)\1.*$""".toRegex()
        val rule3 = """^.*(ab|cd|pq|xy).*${'$'}""".toRegex()
        return input
            .filter(rule1::matches)
            .filter(rule2::matches)
            .filterNot(rule3::matches)
            .size
    }

    fun solvePartTwo(): Int {
        val rule1 = """^.*(..).*\1.*$""".toRegex()
        val rule2 = """^.*(.).\1.*$""".toRegex()
        return input
            .filter(rule1::matches)
            .filter(rule2::matches)
            .size
    }
}
