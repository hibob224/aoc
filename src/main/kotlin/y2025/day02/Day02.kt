package y2025.day02

import template.Puzzle
import template.solve
import utils.factors
import utils.isEven

fun main() = solve { Day02() }

class Day02 : Puzzle<Long, Long>(2025, 2, example = false) {

    override val input = rawInput
        .split(",")
        .map {
            val (start, end) = it.split("-")
            LongRange(start.toLong(), end.toLong())
        }

    override fun solvePartOne(): Long {
        return input.sumOf { range ->
            range
                .filter {
                    val str = it.toString()
                    str.length.isEven && str.take(str.length / 2) == str.substring(str.length / 2)
                }
                .sum()
        }
    }

    override fun solvePartTwo(): Long {
        return input.sumOf outer@{ range ->
            range
                .filter {
                    val str = it.toString()
                    if (str.length == 1) return@filter false // Can't repeat more than once if there is only one digit
                    if (str.toSet().size == 1) return@filter true // Special case for all same digit
                    val factors = str.length.factors()
                    val largestFactor = factors.dropLast(1).last()

                    for (i in largestFactor downTo 2) {
                        if (str.chunked(i).toSet().size == 1) {
                            return@filter true
                        }
                    }
                    false
                }
                .sum()
        }
    }
}
