package y2019.day13

import IntCodeComputer
import utils.getInputFile

fun main() {
    println("Part one: ${Day13.solvePartOne()}")
    println("Part two: ${Day13.solvePartTwo()}")
}

object Day13 {

    private fun parseInput(): List<Long> =
        getInputFile()
            .readLines()[0]
            .split(",")
            .map { it.toLong() }

    fun solvePartOne(): String {
        val outputQueue = mutableListOf<Long>()
        IntCodeComputer(parseInput().toLongArray()).also {
            it.output = { out -> outputQueue.add(out) }
        }.call()

        return outputQueue.chunked(3).count { it[2] == 2L }.toString()
    }

    fun solvePartTwo(): String {
        var score = 0L
        var ballX = 0L
        var paddleX = 0L
        val outputBuffer = mutableListOf<Long>()

        IntCodeComputer(parseInput().toLongArray().also { it[0] = 2 }).also {
            it.input = { (ballX - paddleX).coerceIn(-1L..1L) }
            it.output = { out ->
                outputBuffer.add(out)
                if (outputBuffer.size == 3) {
                    if (outputBuffer[0] == -1L && outputBuffer[1] == 0L) {
                        score = outputBuffer[2]
                    }
                    if (outputBuffer[2] == 4L) {
                        ballX = outputBuffer.first()
                    }
                    if (outputBuffer[2] == 3L) {
                        paddleX = outputBuffer.first()
                    }
                    outputBuffer.clear()
                }
            }
        }.call()

        return score.toString()
    }
}
