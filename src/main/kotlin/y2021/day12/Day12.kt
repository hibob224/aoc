package y2021.day12

import java.io.File

fun main() {
    println("Part one: ${Day12.solvePartOne()}")
    println("Part two: ${Day12.solvePartTwo()}")
}

object Day12 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val cavePaths = mutableMapOf<String, Set<String>>()
        .apply {
            File("src/main/kotlin/$directory/input.txt")
                .readLines()
                .forEach {
                    val (a, b) = it.split("-")
                    put(a, getOrDefault(a, setOf()) + b)
                    put(b, getOrDefault(b, setOf()) + a)
                }
        }

    fun solvePartOne(): Int = search("start", emptyList()).size

    fun solvePartTwo(): Int = search2("start", emptyList(), false).size

    private fun search(node: String, visited: List<String>): List<List<String>> = when (node) {
        "end" -> listOf(visited + node) // Reached the end
        else -> cavePaths[node]!!
            .filter { it != "start" }
            .filter { it.all(Char::isUpperCase) || it !in visited + node } // Filter to only include big caves and non-visited small
            .flatMap { search(it, visited + node) } // Visit all the eligible paths
    }

    private fun search2(node: String, visited: List<String>, visitedSmallTwice: Boolean): List<List<String>> = when (node) {
        "end" -> listOf(visited + node) // Reached the end
        else -> cavePaths[node]!!
            .filter { it != "start" }
            .filter {
                // Filter to only include big caves, unvisited small caves, and visited small caves if we've never second visited a small cave
                it.all(Char::isUpperCase) || it !in visited + node || !visitedSmallTwice
            }
            .flatMap { nextNode ->
                val newVisited = visited + node
                val visitedSmallTwiceNew = visitedSmallTwice ||
                    (newVisited + nextNode)
                        .filter { it.all(Char::isLowerCase) } // Filter to only small caves
                        .groupingBy { it }
                        .eachCount() // Get counts of each cave id
                        .any { it.value == 2 } // Check if any have a count == 2, if we do then no more small caves
                search2(nextNode, newVisited, visitedSmallTwiceNew)
            }
    }
}
