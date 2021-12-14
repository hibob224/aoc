package y2021.day07

import java.io.File
import kotlin.math.absoluteValue

fun main() {
    println("Part one: ${Day07.solvePartOne()}")
    println("Part two: ${Day07.solvePartTwo()}")
}

object Day07 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val input: List<Int> =
        File("src/main/kotlin/$directory/input.txt")
            .readText()
            .split(",")
            .map(String::toInt)

    fun solvePartOne(): Int {
        val minPos = input.minOrNull()!!
        val maxPos = input.maxOrNull()!!

        return (minPos..maxPos).minOf { pos ->
            input.sumOf { (it - pos).absoluteValue }
        }
    }

    fun solvePartTwo(): Int {
        val minPos = input.minOrNull()!!
        val maxPos = input.maxOrNull()!!
        var min = Integer.MAX_VALUE

        (minPos..maxPos).forEach posLoop@{ pos ->
            var posValue = 0
            input.forEach { crab ->
                val positionsMoved = (crab - pos).absoluteValue
                (positionsMoved downTo 1).forEach {
                    posValue += it
                    if (posValue > min) {
                        return@posLoop
                    }
                }
            }
            min = minOf(min, posValue)
        }

        return min
    }
}