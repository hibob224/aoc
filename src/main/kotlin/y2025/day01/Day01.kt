package y2025.day01

import template.Puzzle
import template.solve
import kotlin.math.absoluteValue

fun main() = solve { Day01() }

class Day01 : Puzzle<Int, Int>(2025, 1, example = false) {

    override val input = rawInput.lines().map { line ->
        line.replaceFirstChar { if (line.first() == 'L') "-" else "" }.toInt()
    }

    override fun solvePartOne(): Int {
        var count = 0
        input.fold(50) { acc, v ->
            val newPos = (acc + v).mod(100)
            if (newPos == 0) count++
            newPos
        }
        return count
    }

    override fun solvePartTwo(): Int {
        var count = 0
        input.fold(50) { acc, v ->
            val newPos = acc + v
            count += newPos.absoluteValue / 100
            if (v < 0 && acc != 0 && newPos <= 0) count++
            newPos.mod(100)
        }
        return count
    }
}
