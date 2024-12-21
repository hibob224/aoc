package y2021.day11

import utils.Point
import utils.getInputFile

fun main() {
    println("Part one: ${Day11.solvePartOne()}")
    println("Part two: ${Day11.solvePartTwo()}")
}

object Day11 {

    private val input = getInputFile().readLines()
        .flatMapIndexed { y: Int, row: String ->
            row.mapIndexed { x, c ->
                Point(x, y) to c.toString().toInt()
            }
        }.toMap()

    fun solvePartOne(): Int {
        var flashes = 0
        val cave = input.toMutableMap()

        repeat(100) {
            simulateStep(cave)
            flashes += cave.count { it.value == 0 }
        }

        return flashes
    }

    fun solvePartTwo(): Int {
        val cave = input.toMutableMap()

        repeat(Integer.MAX_VALUE) { step ->
            simulateStep(cave)
            if (cave.all { it.value == 0 }) {
                return step.inc() // Increment because our step count is zero indexed
            }
        }

        throw IllegalStateException("How?")
    }

    private fun simulateStep(cave: MutableMap<Point, Int>) {
        val flashed = mutableListOf<Point>()
        cave.forEach { cave[it.key] = it.value.inc() } // Increment all values by 1

        while (cave.filter { it.value > 9 && it.key !in flashed }.isNotEmpty()) { // Loop until none need to flash
            val flashers = cave.filter { it.value > 9 && it.key !in flashed } // Get all octopuses that need to flash
            flashers.forEach { flasher ->
                flashed += flasher.key
                // Power up the neighbours
                flasher.key
                    .getNeighbours(diagonal = true)
                    .filter { it in cave }
                    .forEach {
                        cave[it] = cave[it]!! + 1
                    }
            }
        }

        flashed.forEach { cave[it] = 0 } // Reset the octopuses that have flashed
    }
}