package y2025.day03

import template.Puzzle
import template.solve

fun main() = solve { Day03() }

class Day03 : Puzzle<Long, Long>(2025, 3, example = false) {

    override val input = rawInput.lines()

    override fun solvePartOne(): Long = solve(2)

    override fun solvePartTwo(): Long = solve(12)

    private fun solve(count: Int): Long {
        return input.sumOf { bank ->
            fun nextBatteryIndex(bank: CharArray, batteryIndex: Int, start: Int): Int {
                val search = bank.dropLast(count - batteryIndex.inc()).drop(start)
                return search.indices.maxBy { search[it] } + start
            }
            val batteries = bank.toCharArray()
            val out = buildList {
                var lastIndex = -1
                repeat(count) {
                    val ind= nextBatteryIndex(batteries, it, lastIndex.inc())
                    add(batteries[ind])
                    lastIndex = ind
                }
            }
            out.joinToString(separator = "").toLong()
        }
    }
}
