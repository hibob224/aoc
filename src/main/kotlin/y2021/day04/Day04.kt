package y2021.day04

import java.io.File

fun main() {
    println("Part one: ${Day04.solvePartOne()}")
    println("Part two: ${Day04.solvePartTwo()}")
}

object Day04 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val calledNumbers: List<Int>
    private val boards: List<BingoBoard>

    init {
        val input = parseInput()
        calledNumbers = parseInput().first().split(",").map(String::toInt)

        val filteredInput = input.toMutableList()
            .filterNot { it.isEmpty() }
            .toMutableList()
        filteredInput.removeAt(0) // Called numbers

        boards = filteredInput.chunked(5) {
            BingoBoard(it)
        }
    }
    private fun parseInput(): List<String> =
        File("src/main/kotlin/$directory/input.txt").readLines()

    fun solvePartOne(): Int {
        calledNumbers.forEach { calledNumber ->
            boards.find { it.numberCalled(calledNumber) }?.let {
                return it.calculateScore() // Winner winner chicken dinner
            }
        }

        throw IllegalStateException("No winner...")
    }

    fun solvePartTwo(): Int {
        val boards = boards.toMutableList()
        val wonBoards = mutableListOf<BingoBoard>()

        calledNumbers.forEach { calledNumber ->
            val winningBoards = boards.filter { it.numberCalled(calledNumber.toInt()) }
            wonBoards.addAll(winningBoards)
            boards.removeAll(winningBoards)
        }

        return wonBoards.last().calculateScore()
    }
}

class BingoBoard(input: List<String>) {

    private val board: List<MutableList<BingoCell>>
    var wonNumber: Int = -1

    init {
        board = input.map {
            it.split(" ")
                .filterNot(String::isEmpty)
                .map { BingoCell(it.toInt()) }
                .toMutableList()
        }
    }

    /**
     * Marks number off, returns true if board has won
     */
    fun numberCalled(number: Int): Boolean {
        var matchX = -1
        var matchY = -1
        board.forEachIndexed boardUpdate@{ x, bingoCells ->
            bingoCells.forEachIndexed { y, bingoCell ->
                if (bingoCell.number == number) {
                    bingoCells[y] = bingoCell.copy(checked = true)
                    matchX = x
                    matchY = y
                    return@boardUpdate
                }
            }
        }

        if (matchX != -1) { // We updated a cell, check if we've won
            val won = board[matchX].all(BingoCell::checked) || // Row win
                    board.all { it[matchY].checked } // Column win
            if (won) {
                wonNumber = number
            }
            return won
        }

        return false
    }

    fun calculateScore(): Int {
        val unmarkedSum = board.flatMap { it.filterNot(BingoCell::checked).map(BingoCell::number) }.sum()
        return unmarkedSum * wonNumber
    }
}

data class BingoCell(val number: Int, val checked: Boolean = false)