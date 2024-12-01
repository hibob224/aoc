package y2024.day01

import utils.getInputFile
import kotlin.math.absoluteValue

fun main() {
    println("Part one: ${Day01.solvePartOne()}")
    println("Part two: ${Day01.solvePartTwo()}")
}

object Day01 {

    private val firstList: List<Long>
    private val secondList: List<Long>

    init {
        val (first, second) = getInputFile(this::class.java.packageName)
            .readLines()
            .map {
                val first = it.substringBefore(' ').toLong()
                val second = it.substringAfterLast(' ').toLong()
                first to second
            }
            .unzip()
        firstList = first.sorted()
        secondList = second.sorted()
    }

    fun solvePartOne(): Long {
        return firstList
            .zip(secondList)
            .sumOf { (it.first - it.second).absoluteValue }
    }

    fun solvePartTwo(): Long {
        return firstList.fold(0) { acc, i ->
            acc + (i * secondList.count { it == i })
        }
    }
}
