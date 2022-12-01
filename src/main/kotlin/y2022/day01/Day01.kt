package y2022.day01

import java.io.File

fun main() {
    println("Part one: ${Day01.solvePartOne()}")
    println("Part two: ${Day01.solvePartTwo()}")
}

object Day01 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val input =
        File("src/main/kotlin/$directory/input.txt")
            .readLines()

    fun solvePartOne(): Int {
        val calorieCounts = mutableListOf<Int>()

        var currentCount = 0

        input.forEach {
            if (it.isEmpty()) {
                calorieCounts += currentCount
                currentCount = 0
            } else {
                currentCount += it.toInt()
            }
        }
        calorieCounts += currentCount

        return calorieCounts.maxOrNull()!!
    }

    fun solvePartTwo(): Int {
        val calorieCounts = mutableListOf<Int>()

        var currentCount = 0

        input.forEach {
            if (it.isEmpty()) {
                calorieCounts += currentCount
                currentCount = 0
            } else {
                currentCount += it.toInt()
            }
        }
        calorieCounts += currentCount

        return calorieCounts.sortedDescending().take(3).sum()
    }
}
