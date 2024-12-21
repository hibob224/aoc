package y2021.day06

import utils.getInputFile

fun main() {
    println("Part one: ${Day06.solvePartOne()}")
    println("Part two: ${Day06.solvePartTwo()}")
}

object Day06 {

    private val input: Map<Int, Long> = getInputFile()
        .readText()
        .split(",")
        .map(String::toInt)
        .groupingBy { it }
        .eachCount()
        .map { it.key to it.value.toLong() }
        .toMap()

    fun solvePartOne(): Long = simulateGenerations(input, 80).values.sum()

    fun solvePartTwo(): Long = simulateGenerations(input, 256).values.sum()

    private fun simulateGenerations(start: Map<Int, Long>, generations: Int): Map<Int, Long> {
        var currentGen = start
        repeat(generations) { currentGen = generation(currentGen) }
        return currentGen
    }

    private fun generation(current: Map<Int, Long>): Map<Int, Long> {
        val newGeneration = mutableMapOf<Int, Long>()
        current.forEach {
            when (it.key) {
                0 -> { // Reproduce
                    newGeneration[6] = newGeneration.getOrDefault(6, 0) + it.value
                    newGeneration[8] = it.value
                }

                else -> newGeneration[it.key.dec()] = newGeneration.getOrDefault(it.key.dec(), 0) + it.value
            }
        }
        return newGeneration
    }
}