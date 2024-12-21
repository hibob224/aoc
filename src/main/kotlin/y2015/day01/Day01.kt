package y2015.day01

import utils.getInputFile

fun main() {
    println("Part one: " + Day01.solvePartOne())
    println("Part two: " + Day01.solvePartTwo())
}

object Day01 {

    private val input = getInputFile().readText()

    fun solvePartOne(): Int = input.count { it == '(' } - input.count { it == ')' }

    fun solvePartTwo(): Int {
        input.foldIndexed(0) { index, acc, c ->
            val newPos = acc + if (c == '(') 1 else -1
            if (newPos < 0) return index + 1
            newPos
        }
        error("404 - Basement not found")
    }
}
