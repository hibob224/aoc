package y2024.day21

import utils.getInputFile
import utils.permutations
import utils.toPointGrid

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

object Day21 {

    private val input = getInputFile(this::class.java.packageName, example = false).readLines()
    private val numericKeypad = listOf("789", "456", "123", ".0A").toPointGrid().toList().filterNot { it.second == '.' }.associate { it.second to it.first }
    private val directionalKeypad = listOf(".^A", "<v>").toPointGrid().toList().filterNot { it.second == '.' }.associate { it.second to it.first }

    fun solvePartOne(): Long = input.sumOf { numeric(it, 2) * it.filter(Char::isDigit).toLong() }

    fun solvePartTwo(): Long {
        return 0
    }

    private fun numeric(sequence: String, limit: Int): Long {
        return sequence.fold(numericKeypad['A']!! to 0L) { (p, s), c ->
            val targ = numericKeypad[c]!!
            val (dx, dy) = p - targ

            val paths = ((if (dx < 0) ">".repeat(-dx) else "<".repeat(dx)) + if (dy < 0) "v".repeat(-dy) else "^".repeat(dy))
                .permutations()
                .toSet()
                .filter { path ->
                    path
                        .asSequence()
                        .runningFold(p) { acc, d ->
                            acc.getNeighbour(d)
                        }
                        .all { it in numericKeypad.values }
                }
                .map { "${it}A" }
                .ifEmpty { listOf("A") }
                .toSet()

            targ to s + paths.minOf { direction(it, limit, 1) }
        }.second
    }

    private fun direction(sequence: String, limit: Int, depth: Int): Long {
        return sequence.fold(directionalKeypad['A']!! to 0L) { (p, s), c ->
            val targ = directionalKeypad[c]!!
            val (dx, dy) = p - targ

            val paths = ((if (dx < 0) ">".repeat(-dx) else "<".repeat(dx)) + if (dy < 0) "v".repeat(-dy) else "^".repeat(dy))
                .permutations()
                .filter { path ->
                    path
                        .asSequence()
                        .runningFold(p) { acc, d ->
                            acc.getNeighbour(d)
                        }
                        .all { it in directionalKeypad.values }
                }
                .map { "${it}A" }
                .ifEmpty { listOf("A") }
                .toSet()

            targ to s + if (limit == depth) paths.minOf { it.length }.toLong() else paths.minOf { direction(it, limit, depth + 1) }
        }.second
    }
}
