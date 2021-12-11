package y2021.day11

import utils.Point
import java.io.File

fun main() {
    println("Part one: ${Day11.solvePartOne()}")
    println("Part two: ${Day11.solvePartTwo()}")
}

object Day11 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private fun parseInput(): List<String> =
        File("src/main/kotlin/$directory/input.txt").readLines()

    fun solvePartOne(): Int {
        var flashes = 0
        val adjacentMoves = listOf(Point(x = 1, y = 0), Point(x = -1, y = 0), Point(x = 0, y = 1), Point(x = 0, y = -1),
            Point(x = 1, y = 1), Point(x = 1, y = -1), Point(x = -1, y = 1), Point(x = -1, y = -1))

        val cave = parseInput().flatMapIndexed { y: Int, row: String ->
            row.mapIndexed { x, c ->
                Point(x, y) to c.toString().toInt()
            }
        }.toMap().toMutableMap()

        repeat(100) {
            val flashed = mutableListOf<Point>()
            cave.forEach { cave[it.key] = it.value.inc() } // Increment all values by 1

            while (cave.filter { it.value > 9 && it.key !in flashed }.isNotEmpty()) {
                val flashers = cave.filter { it.value > 9 && it.key !in flashed }
                flashers.forEach { flasher ->
                    flashed += flasher.key
                    adjacentMoves.forEach { move ->
                        val incrementPos = flasher.key.copy(x = flasher.key.x + move.x, y = flasher.key.y + move.y)
                        if (incrementPos in cave) {
                            cave[incrementPos] = cave[incrementPos]!! + 1
                        }
                    }
                }
            }

            flashes += flashed.size
            flashed.forEach { cave[it] = 0 } // Reset the octopuses that have flashed
        }

        return flashes
    }

    fun solvePartTwo(): Int {
        var flashes = 0
        val adjacentMoves = listOf(Point(x = 1, y = 0), Point(x = -1, y = 0), Point(x = 0, y = 1), Point(x = 0, y = -1),
            Point(x = 1, y = 1), Point(x = 1, y = -1), Point(x = -1, y = 1), Point(x = -1, y = -1))

        val cave = parseInput().flatMapIndexed { y: Int, row: String ->
            row.mapIndexed { x, c ->
                Point(x, y) to c.toString().toInt()
            }
        }.toMap().toMutableMap()

        repeat(Integer.MAX_VALUE) { step ->
            val flashed = mutableListOf<Point>()
            cave.forEach { cave[it.key] = it.value.inc() } // Increment all values by 1

            while (cave.filter { it.value > 9 && it.key !in flashed }.isNotEmpty()) {
                val flashers = cave.filter { it.value > 9 && it.key !in flashed }
                flashers.forEach { flasher ->
                    flashed += flasher.key
                    adjacentMoves.forEach { move ->
                        val incrementPos = flasher.key.copy(x = flasher.key.x + move.x, y = flasher.key.y + move.y)
                        if (incrementPos in cave) {
                            cave[incrementPos] = cave[incrementPos]!! + 1
                        }
                    }
                }
            }

            flashes += flashed.size
            if (flashed.size == cave.size) {
                return step.inc() // Increment, our step starts at 0
            }
            flashed.forEach { cave[it] = 0 } // Reset the octopuses that have flashed
        }

        return flashes
    }
}