package y2025.day09

import template.Puzzle
import template.solve
import utils.Point
import utils.combinations

fun main() = solve { Day09() }

class Day09 : Puzzle<Long, Long>(2025, 9, example = false) {

    override val input = rawInput
        .lines()
        .map { it.split(",") }
        .map { (x, y) -> Point(x.toInt(), y.toInt()) }

    override fun solvePartOne(): Long =
        input.combinations(2)
            .maxOf { (a, b) ->
                (a.x - b.x + 1).toLong() * (a.y - b.y + 1).toLong()
            }

    override fun solvePartTwo(): Long {
        return 0
    }
}
