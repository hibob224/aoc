package y2018.day21

import utils.getInputFile
import y2018.day19.Day19
import y2018.day19.Day19.instructionPattern
import y2018.day19.Instruction

fun main() {
    println("Part one: ${Day21.solvePartOne()}")
    println("Part two: ${Day21.solvePartTwo()}")
}

object Day21 {

    private val file = getInputFile()
    private val instructions = mutableListOf<Instruction>()


    fun solvePartOne(): Int {
        parseInput()

        val ip = file.readLines().first().split("#ip ")[1].toInt()
        val register = IntArray(6)
        register[0] = 12213578

        while (register[ip] in instructions.indices) {
            val instruction = instructions[register[ip]]
            Day19.opcodes[instruction.op]?.invoke(register, instruction)
            register[ip]++
        }

        return register[0]
    }

    fun solvePartTwo(): Int {
        val ip = file.readLines().first().split("#ip ")[1].toInt()
        val register = IntArray(6)
        register[0] = 0
        val seenR4 = mutableSetOf<Int>()

        while (register[ip] in instructions.indices) {
            val instruction = instructions[register[ip]]
            Day19.opcodes[instruction.op]?.invoke(register, instruction)
            if (instruction == Instruction("eqrr", 4, 0, 1)) {
                if (!seenR4.add(register[4])) {
                    break
                }
            }
            register[ip]++
        }

        return seenR4.last()
    }

    private fun parseInput() {
        val lines = file.readLines()
        (1 until lines.size).forEach {
            val (instruction, a, b, c) = instructionPattern.find(lines[it])!!.destructured
            instructions.add(Instruction(instruction, a.toInt(), b.toInt(), c.toInt()))
        }
    }
}