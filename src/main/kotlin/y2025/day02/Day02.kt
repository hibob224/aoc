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
            range.sumOf {
                val str = it.toString()
                if (str.length.isEven) {
                    val (first, second) = str.take(str.length / 2) to str.substring(str.length / 2)
                    if (first == second) {
                        it
                    } else {
                        0L
                    }
                } else {
                    0L
                }
            }
        }
    }

    override fun solvePartTwo(): Long {
        return input.sumOf outer@{ range ->
            range.sumOf {
                val str = it.toString()
                if (str.length == 1) return@sumOf 0 // Can't repeat more than once if there is only one digit
                if (str.toSet().size == 1) return@sumOf it // Special case for all same digit

                val factors = str.length.factors()
                val largestFactor = factors.dropLast(1).last()

                for (i in largestFactor downTo 2) {
                    if (str.chunked(i).toSet().size == 1) {
                        return@sumOf it
                    }
                }

                return@sumOf 0L
            }
        }
    }
}
