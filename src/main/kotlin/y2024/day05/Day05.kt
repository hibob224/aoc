package y2024.day05

import utils.getInputFile

fun main() {
    println("Part one: " + Day05.solvePartOne())
    println("Part two: " + Day05.solvePartTwo())
}

object Day05 {

    private val rules: List<Pair<Long, Long>>
    private val updates: List<List<Long>>

    init {
        val (ruleInput, updateInput) = getInputFile().readText().split("\n\n")
        rules = ruleInput.lines()
            .map {
                val (v1, v2) = it.split('|')
                v1.toLong() to v2.toLong()
            }
        updates = updateInput.lines()
            .map { line -> line.split(',').map { it.toLong() } }
    }

    fun solvePartOne(): Long {
        val validUpdates = updates.filter { update ->
            rules.all { (r1, r2) ->
                val ind1 = update.indexOf(r1)
                val ind2 = update.indexOf(r2)
                ind1 == -1 || ind2 == -1 || ind1 < ind2
            }
        }
        return validUpdates.sumOf { it[it.size / 2] }
    }

    fun solvePartTwo(): Long {
        val invalidUpdates = updates.filterNot { update ->
            rules.all { (r1, r2) ->
                val ind1 = update.indexOf(r1)
                val ind2 = update.indexOf(r2)
                ind1 == -1 || ind2 == -1 || ind1 < ind2
            }
        }
        return invalidUpdates
            .map(::correctOrder)
            .sumOf { it[it.size / 2] }
    }

    private fun correctOrder(update: List<Long>): List<Long> {
        val errorRules = rules.find { (r1, r2) ->
            val ind1 = update.indexOf(r1)
            val ind2 = update.indexOf(r2)
            ind1 != -1 && ind2 != -1 && ind1 > ind2
        }
        return errorRules?.let { (r1, r2) ->
            val i1 = update.indexOf(r1)
            val i2 = update.indexOf(r2)
            update.toMutableList().apply {
                val remove = removeAt(i1)
                add(i2, remove)
            }.let(::correctOrder)
        } ?: update
    }
}
