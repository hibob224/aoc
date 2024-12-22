package y2024.day22

import utils.getInputFile

fun main() {
    println("Part one: " + Day22.solvePartOne())
    println("Part two: " + Day22.solvePartTwo())
}

object Day22 {

    private val input = getInputFile(example = false).readLines().map(String::toLong)

    fun solvePartOne(): Long {
        return input.sumOf {
            var secret = it
            repeat(2000) { secret = secret.nextSecret() }
            secret
        }
    }

    fun solvePartTwo(): Long {
        return input.map {
            val deltas = mutableListOf<Long>(0)
            val prices = mutableListOf(it % 10)
            var secret = it

            repeat(2000) {
                secret = secret.nextSecret()
                val price = secret % 10
                deltas += price - prices.last()
                prices += price
            }

            val windows = deltas.windowed(4).toSet()
            windows.map { window ->
                val index = findSubsequenceIndex(deltas, window)
                window to prices.getOrElse(index + 3) { -1 }
            }
        }.flatten().groupingBy { it.first }.fold(0L) { acc, i ->
            acc + i.second
        }.maxOf { it.value }
    }

    private fun Long.nextSecret(): Long {
        var secret = this.mix(this * 64).prune()
        secret = secret.mix(secret / 32).prune()
        secret = secret.mix(secret * 2048).prune()
        return secret
    }

    private fun Long.mix(l2: Long): Long = this xor l2
    private fun Long.prune(): Long = this % 16777216

    fun findSubsequenceIndex(mainList: List<Long>, subList: List<Long>): Int {
        if (subList.isEmpty()) return 0 // Empty subsequence is always found at the beginning

        for (i in mainList.indices) {
            if (i + subList.size > mainList.size) break // Avoid out-of-bounds access
            if (mainList.subList(i, i + subList.size) == subList) return i
        }
        return -1
    }
}
