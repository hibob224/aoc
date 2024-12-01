package y2024.day01

import utils.getInputFile
import kotlin.math.absoluteValue

fun main() {
    println("Part one: ${Day01.solvePartOne()}")
    println("Part two: ${Day01.solvePartTwo()}")
}

object Day01 {

    private val input = getInputFile(this::class.java.packageName)
        .useLines {
            val leftList = mutableListOf<Long>()
            val rightList = mutableListOf<Long>()
            it.forEach {
                val (left, right) = it.split("   ")
                leftList += left.toLong()
                rightList += right.toLong()
            }
            leftList.sorted() to rightList.sorted()
        }

    fun solvePartOne(): Long {
        return input.first
            .zip(input.second)
            .sumOf { (it.first - it.second).absoluteValue }
    }

    fun solvePartTwo(): Long {
        return input.first.fold(0) { acc, i ->
            acc + (i * input.second.count { it == i })
        }
    }
}
