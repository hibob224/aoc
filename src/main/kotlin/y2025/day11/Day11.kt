package y2025.day11

import template.Puzzle
import template.solve

fun main() = solve { Day11() }

class Day11 : Puzzle<Long, Long>(2025, 11, example = false) {

    override val input = rawInput.lines().associate {
        val (name, connections) = it.split(": ")
        name to connections.split(" ")
    }
    private val cache = mutableMapOf<String, Long>()

    override fun solvePartOne(): Long = countPaths("you")

    override fun solvePartTwo(): Long {
        return 0
    }

    private fun countPaths(start: String): Long {
        if (start == "out") return 1
        if (start in cache.keys) return cache[start]!!
        val outs = input[start].orEmpty()
        val pathCount = outs.sumOf { countPaths(it) }
        cache[start] = pathCount
        return pathCount
    }
}
