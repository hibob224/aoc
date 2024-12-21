package y2019.day02

import utils.getInputFile

fun main() {
    println("Part one: ${Day2.solvePartOne()}")
    println("Part two: ${Day2.solvePartTwo()}")
}

object Day2 {

    private val input = getInputFile().readText().split(",").map(String::toInt)

    fun solvePartOne(): String {
        val partOneInput = ArrayList(input)
        partOneInput[1] = 12
        partOneInput[2] = 2
        return runProgram(partOneInput).toString()
    }

    fun solvePartTwo(): String {
        for (n in 0..99) {
            for (v in 0..99) {
                val input = ArrayList(input)
                input[1] = n
                input[2] = v
                val output = runProgram(input)
                if (output == 19690720) {
                    return (100 * n + v).toString()
                }
            }
        }
        throw IllegalStateException("Didn't find answer")
    }

    private fun runProgram(input: ArrayList<Int>): Int {
        var position = 0
        do {
            val opcode = input[position]
            val inp1 = input[position.inc()]
            val inp2 = input[position.inc().inc()]
            val out = input[position.inc().inc().inc()]

            when (opcode) {
                1 -> input[out] = input[inp1] + input[inp2]
                2 -> input[out] = input[inp1] * input[inp2]
                99 -> return input[0]
                else -> return -1 // Exception
            }

            position += 4
        } while (true)
    }
}
