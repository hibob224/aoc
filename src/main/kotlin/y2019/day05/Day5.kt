package y2019.day05

import IntCodeComputer
import utils.getInputFile

fun main() {
    println("Part one: ${Day5.solvePartOne()}")
    println("Parse two: ${Day5.solvePartTwo()}")
}

object Day5 {

    private fun parseInput(): List<Long> = getInputFile()
        .readLines()[0]
        .split(",")
        .map { it.toLong() }

    fun solvePartOne(): String = IntCodeComputer(parseInput().toLongArray()).also { it.addInput(1) }.call().toString()

    fun solvePartTwo(): String = IntCodeComputer(parseInput().toLongArray()).also { it.addInput(5) }.call().toString()
}
