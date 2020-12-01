package day01

import java.io.File

fun main() {
    println("Part one: ${Day01.solvePartOne()}")
    println("Part two: ${Day01.solvePartTwo()}")
}

object Day01 {

    private fun parseInput(): List<Int> =
        File("src/main/kotlin/day01/input.txt")
            .readLines()
            .map(String::toInt)

    fun solvePartOne(): String {
        val input = parseInput()

        input.forEach { in1 ->
            input.forEach { in2 ->
                if (in1 + in2 == 2020) {
                    return "${in1 * in2}"
                }
            }
        }

        return ""
    }

    fun solvePartTwo(): String {
        val input = parseInput()

        input.forEach { in1 ->
            input.forEach { in2 ->
                input.forEach { in3 ->
                    if (in1 + in2 + in3 == 2020) {
                        return "${in1 * in2 * in3}"
                    }
                }
            }
        }

        return ""
    }
}