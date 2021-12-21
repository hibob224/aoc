package y2021.day21

import java.io.File

fun main() {
    println("Part one: ${Day21.solvePartOne()}")
    println("Part two: ${Day21.solvePartTwo()}")
}

object Day21 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private fun parseInput(): List<String> =
        File("src/main/kotlin/$directory/test.txt").readLines()

    fun solvePartOne(): Int {
        var playerOnePos = 9
        var playerOneScore = 0
        var playerTwoPos = 6
        var playerTwoScore = 0
        var diceRoll = 0
        var rollCount = 0

        while (true) {
            var diceRolls = (1..3).map { diceRoll + it }
            diceRoll += 3
            rollCount += 3
            if (diceRoll > 100) {
                diceRoll -= 100
            }
            playerOnePos += diceRolls.sum()
            while (playerOnePos > 10) {
                playerOnePos -= 10
            }
            playerOneScore += playerOnePos
            if (playerOneScore >= 1000) break

            diceRolls = (1..3).map { diceRoll + it }
            diceRoll += 3
            rollCount += 3
            if (diceRoll > 100) {
                diceRoll -= 100
            }
            playerTwoPos += diceRolls.sum()
            while (playerTwoPos > 10) {
                playerTwoPos -= 10
            }
            playerTwoScore += playerTwoPos
            if (playerTwoScore >= 1000) break
        }

        return minOf(playerOneScore, playerTwoScore) * rollCount
    }

    fun solvePartTwo(): Int {
        return -1
    }
}
