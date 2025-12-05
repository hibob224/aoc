package y2025.day05

import template.Puzzle
import template.solve
import kotlin.math.max
import kotlin.math.min

fun main() = solve { Day05() }

class Day05 : Puzzle<Int, Long>(2025, 5, example = false) {

    override val input = rawInput.split("\n\n")
    private val freshRanges = input[0]
        .split("\n")
        .map {
            val (one, two) = it.split("-")
            LongRange(one.toLong(), two.toLong())
        }
    private val ingredientIds = input[1]
        .split("\n")
        .map { it.toLong() }

    override fun solvePartOne(): Int {
        return ingredientIds.count { id ->
            freshRanges.any { range ->
                id in range
            }
        }
    }

    override fun solvePartTwo(): Long {
        return dedupeRanges(freshRanges)
            .sumOf { (it.last - it.first + 1) }
    }

    private fun dedupeRanges(ranges: List<LongRange>): List<LongRange> {
        val out = ranges.toMutableList()
        var merge = false

        for (i in 0..ranges.size - 2) {
            for (x in i + 1..<ranges.size) {
                val a = out[i]
                val b = out[x]

                val intersect = (a.first in b || a.last in b) || (b.first in a || b.last in a)

                if (intersect) {
                    // Two ranges overlap, take the smallest start and largest end to create a new single range
                    val range = min(a.first, b.first)..max(a.last, b.last)
                    // Replace both ranges with the new range, dupe will be removed later
                    out[i] = range
                    out[x] = range
                    merge = true
                }
            }
        }

        if (!merge) return out // Nothing merged, finished
        return dedupeRanges(out.distinct()) // Recurse to see if more ranges need merged
    }
}
