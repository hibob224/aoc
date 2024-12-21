package y2019.day04

import utils.orFalse

fun main() {
    println("Part one: ${Day4.solvePartOne()}")
    println("Part two: ${Day4.solvePartTwo()}")
}

object Day4 {

    private val range = 240920..789857

    fun solvePartOne(): String {
        return range.count { pw ->
            val zipped = pw.toString().zipWithNext()
            zipped.all { it.first <= it.second } &&
                zipped.any { it.first == it.second }
        }.toString()
    }

    fun solvePartTwo(): String {
        return range.count { pw ->
            val zipped = pw.toString().zipWithNext()
            zipped.all { it.first <= it.second } &&
                zipped.filter { it.first == it.second }
                    .groupBy { it.first }
                    .entries
                    .takeIf { it.isNotEmpty() }
                    ?.any { it.value.size == 1 }
                    .orFalse()
        }.toString()
    }
}
