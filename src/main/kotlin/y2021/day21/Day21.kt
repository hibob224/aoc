package y2021.day21

import utils.Memo2
import utils.getInputFile
import utils.memoize

fun main() {
    println("Part one: ${Day21.solvePartOne()}")
    println("Part two: ${Day21.solvePartTwo()}")
}

object Day21 {

    private val regex = """^Player \d starting position: (\d+)$""".toRegex()
    private val input: List<Int> =
        getInputFile()
            .readLines()
            .map {
                regex.find(it)!!.groupValues[1].toInt()
            }

    init {
        assert(input.size == 2)
    }

    fun solvePartOne(): Int {
        var playerOnePos = input[0]
        var playerOneScore = 0
        var playerTwoPos = input[1]
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

    fun solvePartTwo(): Long {
        val playRound = Memo2<Pair<Int, Int>, Pair<Int, Int>, Pair<Long, Long>>::playRound.memoize()
        val (p1Wins, p2Wins) = playRound(0 to input[0], 0 to input[1])
        return maxOf(p1Wins, p2Wins)
    }
}

private val diracRolls = (1..3).flatMap { a ->
    (1..3).flatMap { b ->
        (1..3).map { c ->
            listOf(a, b, c)
        }
    }
}

private fun Memo2<Pair<Int, Int>, Pair<Int, Int>, Pair<Long, Long>>.playRound(current: Pair<Int, Int>, other: Pair<Int, Int>): Pair<Long, Long> {
    val (currentScore, currentPos) = current
    val (otherScore, otherPos) = other
    // Check if we already know the result for this argument combo in cache, or calculate it

    return if (currentScore >= 21) {
        1L to 0L // P1 already won
    } else if (otherScore >= 21) {
        0L to 1L // P2 already won
    } else {
        // No winner yet, continue playing with every dirac roll combo and keep a tally of who wins
        diracRolls.fold(0L to 0L) { scores, rolls ->
            var newCurrentPos = currentPos + rolls.sum()
            while (newCurrentPos > 10) {
                newCurrentPos -= 10
            }
            val newCurrentScore = currentScore + newCurrentPos
            val (p2Wins, p1Wins) = recurse(otherScore to otherPos, newCurrentScore to newCurrentPos)
            scores.copy(first = scores.first + p1Wins, second = scores.second + p2Wins)
        }
    }
}
