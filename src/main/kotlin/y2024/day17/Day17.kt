package y2024.day17

import utils.getInputFile
import kotlin.math.pow

fun main() {
    println("Part one: " + Day17.solvePartOne())
    println("Part two: " + Day17.solvePartTwo())
    println(3169751 shr 3)
}

object Day17 {

    private val registers: Map<Char, Int>
    private val instructions: List<Int>

    init {
        val (regInput, insInput) = getInputFile(this::class.java.packageName, example = false)
            .readText()
            .split("\n\n")

        val (a, b, c) = regInput
            .split("\n")
            .map {
                it.substringAfter(':').trim().toInt()
            }
        registers = mapOf('A' to a, 'B' to b, 'C' to c)
        instructions = insInput.substringAfter(':').trim().split(",").map { it.toInt() }
    }

    fun solvePartOne(): String {
        val out = mutableListOf<Int>()
        val registers = registers.toMutableMap()
        var instructionPosition = 0

        while (instructionPosition <= instructions.lastIndex) {
            val (opcode, operand) = instructions.subList(instructionPosition, instructionPosition + 2)
            val (nextPosition, outV) = registers.performInstruction(opcode, operand)
            instructionPosition = nextPosition ?: (instructionPosition + 2)
            outV?.let { out += it }
        }

        return out.joinToString(",")
    }


    fun solvePartTwo(): Long {
        return 0
    }

    private fun MutableMap<Char, Int>.performInstruction(opcode: Int, operand: Int): Pair<Int?, Int?> {
        val a = get('A')!!
        val b = get('B')!!
        val c = get('C')!!
        return when (opcode) {
            ADV -> {
                set('A', (a / 2.0.pow(comboOperand(operand))).toInt())
                null to null
            }
            BXL -> {
                set('B', (b xor operand))
                null to null
            }
            BST -> {
                set('B', (comboOperand(operand) % 8))
                null to null
            }
            JNZ -> {
                val newPos = operand.takeIf { a != 0 }
                newPos to null
            }
            BXC -> {
                set('B', b xor c)
                null to null
            }
            OUT -> null to (comboOperand(operand) % 8)
            BDV -> {
                set('B', (a / 2.0.pow(comboOperand(operand))).toInt())
                null to null
            }
            CDV -> {
                set('C', (a / 2.0.pow(comboOperand(operand))).toInt())
                null to null
            }
            else -> error("Unknown instruction: $opcode")
        }
    }

    private fun Map<Char, Int>.comboOperand(operand: Int) = when (operand) {
        in 0..3 -> operand
        4 -> get('A')!!
        5 -> get('B')!!
        6 -> get('C')!!
        else -> error("Illegal combo operand: $operand")
    }

    private const val ADV = 0 // A / combo^2 > A
    private const val BXL = 1 // B XOR literal > B
    private const val BST = 2 // combo % 8 > B
    private const val JNZ = 3 // if A!=0, jump
    private const val BXC = 4 // B XOR C > B
    private const val OUT = 5 // combo % 8, output
    private const val BDV = 6 // A / combo^2 > B
    private const val CDV = 7 // A / combo^2 > C

}
