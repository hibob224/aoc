package y2021.day10

import utils.getInputFile

fun main() {
    println("Part one: ${Day10.solvePartOne()}")
    println("Part two: ${Day10.solvePartTwo()}")
}

object Day10 {

    private val tagMapping = mapOf('(' to ')', '{' to '}', '[' to ']', '<' to '>')
    private val input: List<String> = getInputFile().readLines()

    fun solvePartOne(): Int {
        val illegalChars = mutableListOf<Char>()

        input.forEach lineLoop@{ line ->
            val stack = ArrayDeque<Char>()
            line.forEach {
                if (it in tagMapping.keys) {
                    stack.addFirst(it)
                } else {
                    if (tagMapping[stack.first()] != it) {
                        illegalChars += it
                        return@lineLoop
                    }
                    stack.removeFirst()
                }
            }
        }

        val tagScores = mutableMapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
        return illegalChars.sumOf { tagScores[it]!! }
    }

    fun solvePartTwo(): Long {
        val missingChars = mutableListOf<List<Char>>()
        input.forEach lineLoop@{ line ->
            val stack = ArrayDeque<Char>()
            line.forEach {
                if (it in tagMapping.keys) {
                    stack.addFirst(it)
                } else {
                    if (tagMapping[stack.first()] != it) {
                        return@lineLoop
                    }
                    stack.removeFirst()
                }
            }
            missingChars += stack.map { tagMapping[it]!! }
        }

        val tagScores = mutableMapOf(')' to 1L, ']' to 2L, '}' to 3L, '>' to 4L)
        val scores = missingChars.map {
            it.fold(0L) { acc, c -> acc * 5L + tagScores[c]!! }
        }.sorted()
        val middle = scores.size / 2
        return scores[middle]
    }
}