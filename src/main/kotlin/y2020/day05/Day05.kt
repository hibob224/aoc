package y2020.day05

import utils.getInputFile
import utils.lowerRange
import utils.upperRange

fun main() {
    println("Part one: ${Day05.solvePartOne()}")
    println("Part two: ${Day05.solvePartTwo()}")
}

object Day05 {

    private val seats =
        getInputFile(this::class.java.packageName)
            .readLines()
            .map { ticket ->
                var rowRange = 0..127
                var columnRange = 0..7
                ticket.forEach {
                    when (it) {
                        'F' -> rowRange = rowRange.lowerRange
                        'B' -> rowRange = rowRange.upperRange
                        'L' -> columnRange = columnRange.lowerRange
                        'R' -> columnRange = columnRange.upperRange
                    }
                }
                rowRange.first * 8 + columnRange.first
            }
            .sorted()

    fun solvePartOne(): String = seats.maxOrNull().toString()

    fun solvePartTwo(): String = seats.filterIndexed { index, i ->
        if (index == 0 || index == seats.lastIndex) {
            false
        } else {
            // Check the next seat ID has a one space gap, that'll be our seat!
            seats[index.inc()] == i + 2
        }
    }.first().inc().toString()
}