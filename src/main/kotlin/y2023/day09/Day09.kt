package y2023.day09

import utils.getInputFile

fun main() {
    println("Part one: ${Day09.solvePartOne()}")
    println("Part two: ${Day09.solvePartTwo()}")
}

object Day09 {

    private val input =
        getInputFile()
            .readLines()
            .map { it.split(" ").map(String::toLong) }
            .map {
                buildList<List<Long>> {
                    add(it)

                    while (last().any { it != 0L }) {
                        add(last().windowed(2).map { it[1] - it[0] })
                    }
                }
            }

    fun solvePartOne(): Long =
        input.sumOf { it.sumOf(List<Long>::last) }

    fun solvePartTwo(): Long =
        input
            .map { it.map(List<Long>::first).reversed() }
            .sumOf { it.reduce { acc, l -> l - acc } }
}
