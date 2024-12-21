package y2018.day01

import utils.getInputFile
import utils.repeat

fun main() {
    println("Part one: ${Day1.solvePartOne()}")
    println("Parse two: ${Day1.solvePartTwo()}")
}

object Day1 {

    private val input = getInputFile(this::class.java.packageName).readLines()

    /**
     * Will sum all the deltas to return the final frequency
     */
    fun solvePartOne(): Int = input.asSequence().map { it.toInt() }.sum()

    /**
     * Will return the first duplicated frequency
     */
    fun solvePartTwo(): Int {
        val frequencies = mutableSetOf<Int>()
        var total = 0
        input.map { it.toInt() }.asSequence().repeat().forEach {
            total += it
            if (!frequencies.add(total)) {
                return total
            }
        }
        throw IllegalStateException("Escaped infinite loop")
    }
}