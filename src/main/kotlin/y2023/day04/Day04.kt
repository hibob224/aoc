package y2023.day04

import utils.getInputFile
import kotlin.math.pow

fun main() {
    println("Part one: ${Day04.solvePartOne()}")
    println("Part two: ${Day04.solvePartTwo()}")
}

object Day04 {

    private val input =
        getInputFile()
            .readLines()
            .map { it.split(": ")[1] }
            .map { it.split(" | ") }
            .mapIndexed { index, numbers ->
                ScratchCard(
                    index,
                    numbers[0].split(" ").filterNot(String::isEmpty).map(String::toInt),
                    numbers[1].split(" ").filterNot(String::isEmpty).map(String::toInt),
                )
            }

    fun solvePartOne(): Int {
        return input
            .filter { it.matches() > 0 }
            .sumOf { 2.0.pow(it.matches().dec()) }
            .toInt()
    }

    fun solvePartTwo(): Int {
        val cards = input.map {
            it.copy(
                wonCardIndexes = if (it.matches() > 0) input.subList(it.cardIndex.inc(), it.cardIndex + it.matches().inc()).map(ScratchCard::cardIndex) else emptyList()
            )
        }
        return cards.flatMap { cards.matches(cards[it.cardIndex]) }.size
    }

    private fun List<ScratchCard>.matches(index: ScratchCard): List<ScratchCard> =
        index.wonCardIndexes.flatMap { matches(this[it]) } + index

    data class ScratchCard(
        val cardIndex: Int,
        private val winningNumbers: List<Int>,
        private val numbers: List<Int>,
        val wonCardIndexes: List<Int> = emptyList(),
    ) {
        fun matches(): Int = numbers.count { it in winningNumbers }
    }
}
