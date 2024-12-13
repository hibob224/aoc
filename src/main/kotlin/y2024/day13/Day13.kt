package y2024.day13

import utils.getInputFile

fun main() {
    println("Part one: " + Day13.solvePartOne())
    println("Part two: " + Day13.solvePartTwo())
}

object Day13 {

    private val buttonRegex = """X\+(\d+), Y\+(\d+)""".toRegex()
    private val prizeRegex = """X=(\d+), Y=(\d+)""".toRegex()
    private val input = getInputFile(this::class.java.packageName, example = false)
        .readLines()
        .filterNot { it.isBlank() }
        .chunked(3)
        .map { (a, b, prize) ->
            val (_, aX, aY) = buttonRegex.find(a)!!.groupValues
            val (_, bX, bY) = buttonRegex.find(b)!!.groupValues
            val (_, pX, pY) = prizeRegex.find(prize)!!.groupValues

            Triple(
                aX.toLong() to aY.toLong(),
                bX.toLong() to bY.toLong(),
                pX.toLong() to pY.toLong()
            )
        }

    fun solvePartOne(): Long {
        return input
            .mapNotNull { (a, b, p) -> cost(a, b, p) }
            .sum()
    }

    fun solvePartTwo(): Long {
        val big = 10_000_000_000_000
        return input
            .map { (a, b, p) -> Triple(a, b, p.first + big to p.second + big) } // Add 10t to all target values
            .mapNotNull { (a, b, p) -> cost(a, b, p) }
            .sum()
    }

    private fun cost(
        a: Pair<Long, Long>,
        b: Pair<Long, Long>,
        prize: Pair<Long, Long>,
    ): Long? {
        val (aX, aY) = a
        val (bX, bY) = b
        val (pX, pY) = prize

        val aClick = (pX * bY - pY * bX).toDouble() / (aX * bY - bX * aY)
        val bClick = (pX - aX * aClick) / bX
        return if (aClick % 1 != 0.0 || bClick % 1 != 0.0) { // Make sure a+b clicks are round, can't half click a button
            null // Game can't be won
        } else {
            aClick.toLong() * 3 + bClick.toLong()
        }
    }
}
