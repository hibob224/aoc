package y2024.day02

import utils.getInputFile
import kotlin.math.absoluteValue

fun main() {
    println("Part one: ${Day02.solvePartOne()}")
    println("Part two: ${Day02.solvePartTwo()}")
}

object Day02 {

    private val input = getInputFile(this::class.java.packageName)
        .readLines()
        .map { it.split(' ').map { it.toInt() } }

    fun solvePartOne(): Long = input.count(::isValid).toLong()

    fun solvePartTwo(): Long {
        return input.count {
            if (isValid(it)) {
                true
            } else {
                // Can't be bothered doing something smart to figure out which can be removed to fix things, so just try every option
                repeat(it.size) { i ->
                    val edited = it.toMutableList().also { it.removeAt(i) }
                    if (isValid(edited)) {
                        return@count true
                    }
                }
                false
            }
        }.toLong()
    }

    private fun isValid(list: List<Int>): Boolean {
        val allAscDesc = list.sorted() == list || list.sortedDescending() == list
        val diff = list
            .windowed(2)
            .all { (it[0] - it[1]).absoluteValue in 1..3 }
        return allAscDesc && diff
    }
}
