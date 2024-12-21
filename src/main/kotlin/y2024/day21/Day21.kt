package y2024.day21

import utils.*

fun main() {
    println("Part one: " + Day21.solvePartOne())
    println("Part two: " + Day21.solvePartTwo())
}

/**
 * +---+---+---+
 * | 7 | 8 | 9 |
 * +---+---+---+
 * | 4 | 5 | 6 |
 * +---+---+---+
 * | 1 | 2 | 3 |
 * +---+---+---+
 *     | 0 | A |
 *     +---+---+
 */

/**
 *     +---+---+
 *     | ^ | A |
 * +---+---+---+
 * | < | v | > |
 * +---+---+---+
 */
private val numericKeypad = listOf("789", "456", "123", ".0A").toPointGrid().toList().filterNot { it.second == '.' }.associate { it.second to it.first }
private val directionalKeypad = listOf(".^A", "<v>").toPointGrid().toList().filterNot { it.second == '.' }.associate { it.second to it.first }

object Day21 {

    private val input = getInputFile(this::class.java.packageName).readLines()

    fun solvePartOne(): Long = solve(2)

    fun solvePartTwo(): Long = solve(25)

    private fun solve(limit: Int): Long = input.sumOf { direction(it, limit, 0) * it.filter(Char::isDigit).toLong() }

    private val direction = Memo3<String, Int, Int, Long>::shortestSequence.memoize()
}

private fun Memo3<String, Int, Int, Long>.shortestSequence(sequence: String, limit: Int, depth: Int): Long {
    val keypad = if (depth == 0) numericKeypad else directionalKeypad
    return sequence.fold(keypad['A']!! to 0L) { (p, s), c ->
        val targ = keypad[c]!!
        val (dx, dy) = p - targ

        val paths = ((if (dx < 0) ">".repeat(-dx) else "<".repeat(dx)) + if (dy < 0) "v".repeat(-dy) else "^".repeat(dy)) // Create base sequence, all these directions are required to reach our target
            .permutations() // Create all permutations of these directions, all permutations will reach the target
            .filter { path -> // But some permutations will lead to positions that are not buttons, which breaks the robots, filter those out
                path
                    .asSequence()
                    .runningFold(p) { acc, d ->
                        acc.getNeighbour(d)
                    }
                    .all { it in keypad.values }
            }
            .map { "${it}A" } // Need 'A' at the end of each sequence to push the target button
            .ifEmpty { listOf("A") } // If we have no paths, then we were already at the target, just push it
            .toSet() // Remove duplicates

        targ to s + if (limit == depth) paths.minOf { it.length }.toLong() else paths.minOf { recurse(it, limit, depth + 1) }
    }.second
}
