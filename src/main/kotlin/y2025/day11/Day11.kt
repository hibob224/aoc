package y2025.day11

import template.Puzzle
import template.solve
import utils.Memo3
import utils.memoize

fun main() = solve { Day11() }

class Day11 : Puzzle<Long, Long>(2025, 11, example = false) {

    override val input = rawInput.lines().associate {
        val (name, connections) = it.split(": ")
        name to connections.split(" ")
    }

    override fun solvePartOne(): Long {
        return Memo3<Map<String, List<String>>, String, List<String>, Long>::pathCount.memoize()(input, "you", listOf())
    }

    override fun solvePartTwo(): Long {
        return Memo3<Map<String, List<String>>, String, List<String>, Long>::pathCount.memoize()(input, "svr", listOf("fft", "dac"))
    }
}

private fun Memo3<Map<String, List<String>>, String, List<String>, Long>.pathCount(
    input: Map<String, List<String>>,
    current: String,
    requiredNodes: List<String>,
): Long {
    if (current == "out") return if (requiredNodes.isEmpty()) 1 else 0
    val newRequire = if (current in requiredNodes) requiredNodes - current else requiredNodes
    val outs = input[current].orEmpty()
    val pathCount = outs.sumOf { recurse(input, it, newRequire) }
    return pathCount
}
