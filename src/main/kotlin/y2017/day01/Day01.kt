package y2017.day01

import utils.getInputFile

fun main() {
    println("Part one: " + Day01.solvePartOne())
    println("Part two: " + Day01.solvePartTwo())
}

object Day01 {

    private val input = getInputFile().readText()

    fun solvePartOne(): Int {
        var sum = 0
        input.forEachIndexed { index, number ->
            val testIndex = if (index == input.length - 1) 0 else index + 1
            if (number == input[testIndex]) {
                sum += number.toString().toInt()
            }
        }
        return sum
    }

    fun solvePartTwo(): Int {
        var sum = 0
        input.forEachIndexed { index, number ->
            var testIndex = index + (input.length / 2)
            if (testIndex >= input.length) {
                testIndex -= input.length
            }
            if (number == input[testIndex]) {
                sum += number.toString().toInt()
            }
        }
        return sum
    }
}
