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
            generateSequence(it) { it.nextSecret() }.drop(2000).first()
        }
    }

    fun solvePartTwo(): Long {
        return input
            .map { start ->
                val secrets = generateSequence(start) { it.nextSecret() }.take(2001).map { it % 10 }.toList()
                val deltas = listOf(0) + secrets.zipWithNext { a, b -> b - a }
                deltas
                    .windowed(4)
                    .mapIndexed { index, window -> window to secrets[index + 3] }
                    .distinctBy { it.first }
            }
            .flatten()
            .groupingBy { it.first }
            .fold(0L) { acc, i -> acc + i.second }
            .maxOf { it.value }
    }

    private fun Long.nextSecret(): Long {
        var secret = this.mix(this * 64).prune()
        secret = secret.mix(secret / 32).prune()
        secret = secret.mix(secret * 2048).prune()
        return secret
    }

    private fun Long.mix(l2: Long): Long = this xor l2
    private fun Long.prune(): Long = this % 16777216
}
