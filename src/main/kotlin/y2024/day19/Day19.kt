package y2024.day19

import utils.Memo2
import utils.getInputFile
import utils.memoize

fun main() {
    println("Part one: " + Day19.solvePartOne())
    println("Part two: " + Day19.solvePartTwo())
}

object Day19 {

    private val towelSupply: Set<String>
    private val desiredTowels: Set<String>

    init {
        val (supplyInput, desireInput) =
            getInputFile(this::class.java.packageName, example = false)
                .readText()
                .split("\n\n")
        towelSupply = supplyInput.split(", ").toSet()
        desiredTowels = desireInput.split("\n").toSet()
    }

    fun solvePartOne(): Int {
        return desiredTowels.count {
            isTowelPossible(it)
        }
    }

    private fun isTowelPossible(desired: String): Boolean =
        towelSupply.any {
            when {
                it == desired -> true
                desired.startsWith(it) -> isTowelPossible(desired.removePrefix(it))
                else -> false
            }
        }

    fun solvePartTwo(): Long {
        val optionCount = Memo2<Set<String>, String, Long>::optionCount.memoize()
        return desiredTowels.sumOf { optionCount(towelSupply, it) }
    }
}

private fun Memo2<Set<String>, String, Long>.optionCount(
    towelSuppy: Set<String>,
    desired: String,
): Long {
    val options = towelSuppy.sumOf {
        when {
            it == desired -> 1L
            desired.startsWith(it) -> recurse(towelSuppy, desired.removePrefix(it))
            else -> 0L
        }
    }
    return options
}
