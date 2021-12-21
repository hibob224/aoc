package y2021.day21

import java.io.File

fun main() {
    println("Part one: ${Day21.solvePartOne()}")
    println("Part two: ${Day21.solvePartTwo()}")
}

object Day21 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val regex = """^Player \d starting position: (\d+)$""".toRegex()
    private val input: List<Int> =
        File("src/main/kotlin/$directory/input.txt")
            .readLines()
            .map {
                regex.find(it)!!.groupValues[1].toInt()
            }

    init {
        assert(input.size == 2)
    }

    private val diracRolls = (1..3).flatMap { a ->
        (1..3).flatMap { b ->
            (1..3).map { c ->
                listOf(a, b, c)
            }
        }
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
        val (p1Wins, p2Wins) = playRound(0, input[0], 0, input[1])
        return maxOf(p1Wins, p2Wins)
    }

    // Memoization cache
    private val cache = mutableMapOf<Pair<Pair<Int, Int>, Pair<Int, Int>>, Pair<Long, Long>>()

    private fun playRound(currentScore: Int, currentPos: Int, otherScore: Int, otherPos: Int): Pair<Long, Long> {
        val cacheKey = (currentScore to currentPos) to (otherScore to otherPos)
        // Check if we already know the result for this argument combo in cache, or calculate it
        return cache.computeIfAbsent(cacheKey) {
            return@computeIfAbsent if (currentScore >= 21) {
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
                    val (p2Wins, p1Wins) = playRound(otherScore, otherPos, newCurrentScore, newCurrentPos)
                    scores.copy(first = scores.first + p1Wins, second = scores.second + p2Wins)
                }
            }
        }
    }
}
