package y2024.day23

import utils.Memo2
import utils.getInputFile
import utils.memoize

fun main() {
    val day = Day23()
    println("Part one: " + day.solvePartOne())
    println("Part two: " + day.solvePartTwo())
}

class Day23 {

    private val adjacent = mutableMapOf<String, MutableList<String>>().withDefault { mutableListOf() }

    init {
        getInputFile(example = false)
            .forEachLine {
                val (a, b) = it.split("-")
                adjacent.getOrPut(a, ::mutableListOf).add(b)
                adjacent.getOrPut(b, ::mutableListOf).add(a)
            }
    }

    fun solvePartOne(): Int {
        return adjacent.keys.sumOf { a ->
            adjacent.getValue(a).sumOf b@{ b ->
                if (a >= b) return@b 0
                adjacent.getValue(a).intersect(adjacent.getValue(b).toSet()).count { c ->
                    if (c >= b) return@count false
                    a.startsWith("t") || b.startsWith("t") || c.startsWith("t")
                }
            }
        } / 2
    }

    fun solvePartTwo(): String {
        var bestSolution = setOf<String>()
        val entries = adjacent.entries.toList()

        fun check(solution: Set<String>, index: Int) {
            val (id, adj) = entries.getOrNull(index) ?: return
            if (adj.containsAll(solution)) {
                if (solution.size.inc() > bestSolution.size) {
                    bestSolution = solution + id
                }
                check(solution + id, index + 1)
            }
            check(solution, index + 1)
        }

        check(emptySet(), 0)
        return bestSolution.sorted().joinToString(",")
    }

    private val fillParty = Memo2<Map<String, List<String>>, Set<String>, List<Set<String>>>::fillParty.memoize()

}

private fun Memo2<Map<String, List<String>>, Set<String>, List<Set<String>>>.fillParty(adjacent: Map<String, List<String>>, party: Set<String>): List<Set<String>> {
    val intersect = party.fold(adjacent.getValue(party.first()).toSet()) { acc, s -> acc.intersect(adjacent.getValue(s)) }
    return if (intersect.isEmpty()) {
        listOf(party)
    } else {
        intersect.flatMap { recurse(adjacent, party + it) }
    }
}
