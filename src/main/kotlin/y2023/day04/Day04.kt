package y2023.day04

import utils.getInputFile
import kotlin.math.pow

fun main() {
    println("Part one: ${Day04.solvePartOne()}")
    println("Part two: ${Day04.solvePartTwo()}")
}

object Day04 {

    private val input =
        getInputFile(this::class.java.packageName, example = false)
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
        return input.size + input.flatMap { it.getCards() }.size
    }

    private fun ScratchCard.getCards(): List<ScratchCard> =
        if (matches() > 0) {
            val wonCopies = input.subList(cardIndex.inc(), cardIndex + matches().inc())
            wonCopies + wonCopies.flatMap { it.getCards() }
        } else {
            emptyList()
        }

    data class ScratchCard(
        val cardIndex: Int,
        private val winningNumbers: List<Int>,
        private val numbers: List<Int>,
    ) {
        fun matches(): Int = numbers.count { it in winningNumbers }
    }
}
