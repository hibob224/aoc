package y2023.day07

import utils.getInputFile

fun main() {
    println("Part one: ${Day07.solvePartOne()}")
    println("Part two: ${Day07.solvePartTwo()}")
}

object Day07 {

    private val input =
        getInputFile(this::class.java.packageName, example = false)
            .readLines()
            .map {
                val (hand, bid) = it.split(" ", limit = 2)
                Hand(hand.toList(), bid.toLong())
            }

    private val values = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2').reversed()

    fun solvePartOne(): Long {
        return input
            .sortedWith(compareBy<Hand> {
                it.handValue
            }.thenComparator { a, b ->
                a.hand
                    .zip(b.hand)
                    .firstOrNull { it.first != it.second }
                    ?.let {
                        values.indexOf(it.first).compareTo(values.indexOf(it.second))
                    } ?: 0
            })
            .map { it.bid }
            .reduceIndexed { index, acc, l -> acc + index.inc() * l }
    }

    fun solvePartTwo(): Long = 0

    data class Hand(
        val hand: List<Char>,
        val bid: Long,
    ) {
        val handValue = hand
            .groupBy { it }
            .let {
                when {
                    it.size == 1 -> 7
                    it.size == 2 -> if (it.values.any { it.size == 4 }) 6 else 5
                    it.size == 3 -> if (it.values.any { it.size == 3 }) 4 else 3
                    it.size == 4 -> 2
                    it.size == 5 -> 1
                    else -> error("Illegal")
                }
            }
    }
}
