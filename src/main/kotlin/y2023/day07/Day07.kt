package y2023.day07

import utils.getInputFile
import utils.orZero

fun main() {
    println("Part one: ${Day07.solvePartOne()}")
    println("Part two: ${Day07.solvePartTwo()}")
}

object Day07 {

    private val input =
        getInputFile()
            .readLines()
            .map {
                val (hand, bid) = it.split(" ", limit = 2)
                Hand(hand.toList(), bid.toLong())
            }

    fun solvePartOne(): Long {
        val cardValue = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2').reversed()
        return input
            .sortedWith(compareBy<Hand> {
                it.handValue
            }.thenComparator { a, b ->
                a.hand
                    .zip(b.hand)
                    .firstOrNull { it.first != it.second }
                    ?.let {
                        cardValue.indexOf(it.first).compareTo(cardValue.indexOf(it.second))
                    } ?: 0
            })
            .map { it.bid }
            .reduceIndexed { index, acc, l -> acc + index.inc() * l }
    }

    // 251411390 too high
    fun solvePartTwo(): Long {
        val cardValue = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J').reversed()
        return input
            .sortedWith(compareBy<Hand> {
                it.handValue2
            }.thenComparator { a, b ->
                a.hand
                    .zip(b.hand)
                    .firstOrNull { it.first != it.second }
                    ?.let {
                        cardValue.indexOf(it.first).compareTo(cardValue.indexOf(it.second))
                    } ?: 0
            })
            .map { it.bid }
            .reduceIndexed { index, acc, l -> acc + index.inc() * l }
    }

    data class Hand(
        val hand: List<Char>,
        val bid: Long,
    ) {
        val handValue = hand
            .groupBy { it }
            .let {
                when (it.size) {
                    1 -> 7
                    2 -> if (it.values.any { it.size == 4 }) 6 else 5
                    3 -> if (it.values.any { it.size == 3 }) 4 else 3
                    4 -> 2
                    5 -> 1
                    else -> error("Illegal")
                }
            }
        val handValue2 = hand
            .groupBy { it }
            .let {
                val jokerCount = it['J']?.size.orZero()
                if (jokerCount == 0) {
                    handValue
                } else {
                    when (it.size) {
                        1 -> 7
                        2 -> 7
                        3 -> if (jokerCount == 2 || it.values.any { it.size == 3 }) 6 else 5
                        4 -> 4
                        5 -> 2
                        else -> error("Illegal")
                    }
                }
            }
    }
}
