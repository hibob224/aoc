package y2024.day02

import utils.getInputFile

fun main() {
    println("Part one: ${Day02.solvePartOne()}")
    println("Part two: ${Day02.solvePartTwo()}")
}

object Day02 {

    private val input = getInputFile()
        .readLines()
        .map { it.split(' ').map(String::toInt) }

    fun solvePartOne(): Long = input.count(::isValid).toLong()

    fun solvePartTwo(): Long {
        return input.count { report ->
            report.indices.any {
                isValid(report.toMutableList().apply { removeAt(it) })
            }
        }.toLong()
    }

    private fun isValid(list: List<Int>): Boolean {
        val allAsc = list
            .zipWithNext { a, b -> (a - b) }
            .all { it in 1..3 }
        val allDesc = list
            .zipWithNext { a, b -> (a - b) }
            .all { it in -1 downTo -3 }
        return allAsc || allDesc
    }
}
