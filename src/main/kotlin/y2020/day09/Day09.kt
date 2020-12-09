package y2020.day09

import java.io.File
import java.lang.IllegalStateException

fun main() {
    val partOne = Day09.solvePartOne()
    println("Part one: $partOne")
    println("Part two: ${Day09.solvePartTwo(partOne)}")
}

object Day09 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val input = File("src/main/kotlin/$directory/input.txt")
        .readLines()
        .map(String::toLong)

    fun solvePartOne(): Long {
        val consideration = 25
        for (i in consideration..input.lastIndex) {
            val value = input[i]
            val inputs = input.subList(i - consideration, i)

            if (elementPairs(inputs).none { it.first + it.second == value }) {
                return value
            }
        }

        throw IllegalStateException("No solution")
    }

    fun solvePartTwo(target: Long): String {
        return (2..input.size).asSequence()
            .flatMap { input.windowed(it) }
            .first { it.sum() == target }
            .let { it.minOrNull()!! + it.maxOrNull()!! }
            .toString()
    }
}

fun <T> elementPairs(arr: List<T>): Sequence<Pair<T, T>> = sequence {
    for(i in 0 until arr.size-1)
        for(j in i+1 until arr.size)
            yield(arr[i] to arr[j])
}