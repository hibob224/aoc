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
            isTowelPossible("", it)
        }
    }

    private fun isTowelPossible(currentTowel: String, desired: String): Boolean {
        return towelSupply.any {
            val stitched = currentTowel + it
            if (stitched == desired) {
                true
            } else if (!desired.startsWith(stitched)) {
                false
            } else {
                isTowelPossible(currentTowel + it, desired)
            }
        }
    }

    fun solvePartTwo(): Long {
        return 0
    }
}
