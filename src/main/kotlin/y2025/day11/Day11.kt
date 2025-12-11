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

    override fun solvePartOne(): Long = /*countPaths("you")*/0

    override fun solvePartTwo(): Long {
        cache.clear()
        return countPaths("svr", listOf("fft", "dac"))
    }

    private fun countPaths(start: String, requiredNodes: List<String> = emptyList()): Long {
//        println("Start: $start, Req: $requiredNodes")
        if (start == "out") return if (requiredNodes.isEmpty()) 1 else 0
        if (start+requiredNodes in cache.keys) return cache[start+requiredNodes]!!
        val newRequire = if (start in requiredNodes) requiredNodes - start else requiredNodes
        val outs = input[start].orEmpty()
        val pathCount = outs.sumOf { countPaths(it, newRequire) }
        cache[start+requiredNodes] = pathCount
        return pathCount
    }
}
