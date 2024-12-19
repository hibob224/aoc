package y2024.day19

import utils.getInputFile

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

    fun solvePartTwo(): Long = desiredTowels.sumOf(::optionCount)

    private fun optionCount(
        desired: String,
        cache: HashMap<String, Long> = HashMap(),
    ): Long {
        if (desired in cache.keys) return cache[desired]!!

        val options = towelSupply.sumOf {
            when {
                it == desired -> 1L
                desired.startsWith(it) -> optionCount(desired.removePrefix(it), cache)
                else -> 0L
            }
        }

        cache[desired] = options
        return options
    }
}
