package y2020.day08

import java.io.File
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

fun main() {
    println("Part one: ${Day08.solvePartOne()}")
    println("Part two: ${Day08.solvePartTwo()}")
}

object Day08 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val regex = """^(nop|acc|jmp) ([+-]\d+)${'$'}""".toRegex()
    private fun parseInput(): List<Instruction> =
        File("src/main/kotlin/$directory/input.txt")
            .readLines()
            .map { line ->
                regex.matchEntire(line)?.let {
                    val (instruction, value) = it.destructured
                    when (instruction) {
                        "acc" -> Instruction.Acc(value.toInt())
                        "jmp" -> Instruction.Jump(value.toInt())
                        "nop" -> Instruction.NoOp(value.toInt())
                        else -> throw IllegalArgumentException("Unknown instruction: $instruction")
                    }
                }
            }
            .requireNoNulls()

    fun solvePartOne(): String {
        val ranInstructions = mutableSetOf<Int>()
        val instructions = parseInput()
        var index = 0
        var acc = 0

        while (true) {
            if (index in ranInstructions) {
                break
            }
            ranInstructions.add(index)

            when (val instruction = instructions[index]) {
                is Instruction.Acc -> {
                    acc += instruction.value
                    index++
                }
                is Instruction.Jump -> index += instruction.value
                is Instruction.NoOp -> index++
            }
        }

        return acc.toString()
    }

    fun solvePartTwo(): String {
        val instructions = parseInput()

        instructions.forEachIndexed { index, instruction ->
            val inInstruction = when (instruction) {
                is Instruction.Acc -> return@forEachIndexed
                is Instruction.Jump -> Instruction.NoOp(instruction.value)
                is Instruction.NoOp -> Instruction.Jump(instruction.value)
            }
            val ranInstructions = mutableSetOf<Int>()
            var i = 0
            var acc = 0

            while (true) {
                if (i in ranInstructions) break
                ranInstructions.add(i)

                if (i > instructions.lastIndex.inc()) {
                    break
                } else if (i == instructions.lastIndex.inc()) {
                    return acc.toString()
                }

                when (val ins = (if (i == index) inInstruction else instructions[i])) {
                    is Instruction.Acc -> {
                        acc += ins.value
                        i++
                    }
                    is Instruction.Jump -> i += ins.value
                    is Instruction.NoOp -> i++
                }
            }
        }

        throw IllegalStateException("Didn't get answer")
    }

    sealed class Instruction(val value: Int) {
        class Acc(value: Int) : Instruction(value)
        class Jump(value: Int) : Instruction(value)
        class NoOp(value: Int) : Instruction(value)
    }
}