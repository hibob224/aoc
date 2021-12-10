package y2021.day10

import java.io.File

fun main() {
    println("Part one: ${Day10.solvePartOne()}")
    println("Part two: ${Day10.solvePartTwo()}")
}

object Day10 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private fun parseInput(): List<String> =
        File("src/main/kotlin/$directory/input.txt").readLines()

    fun solvePartOne(): Int {
        val openTags = listOf('(', '{', '[', '<')
        val closeTags = listOf(')', '}', ']', '>')

        val illegalChars = mutableListOf<Char>()

        parseInput().forEach lineLoop@{
            val stack = ArrayDeque<Char>()
            it.forEach {
                if (it in openTags) {
                    stack.addFirst(it)
                } else if (it in closeTags) {
                    if (closeTags[openTags.indexOf(stack.first())] != it) {
                        illegalChars += it
                        return@lineLoop
                    }
                    stack.removeFirst()
                }
            }
        }

        return illegalChars.sumBy {
            when (it) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> throw IllegalStateException("Illegal illegal char")
            }
        }
    }

    fun solvePartTwo(): Long {
        val openTags = listOf('(', '{', '[', '<')
        val closeTags = listOf(')', '}', ']', '>')

        val incomplete = mutableListOf<ArrayDeque<Char>>()
        parseInput().forEach lineLoop@{
            val stack = ArrayDeque<Char>()
            it.forEach {
                if (it in openTags) {
                    stack.addFirst(it)
                } else if (it in closeTags) {
                    if (closeTags[openTags.indexOf(stack.first())] != it) {
                        return@lineLoop
                    }
                    stack.removeFirst()
                }
            }
            incomplete += stack
        }

        // Now complete the stacks
        val missingChars = incomplete.map {
            it.map { closeTags[openTags.indexOf(it)] }
        }

        val scores = missingChars.map {
            it.fold(0L) { acc, c ->
                acc * 5L + when (c) {
                    ')' -> 1L
                    ']' -> 2L
                    '}' -> 3L
                    '>' -> 4L
                    else -> throw IllegalStateException("Illegal illegal char")
                }
            }
        }.sorted()
        val middle = scores.size / 2
        return scores[middle]
    }
}