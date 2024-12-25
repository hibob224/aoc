package y2024.day25

import utils.getInputFile
import utils.toPointGrid

fun main() {
    println("Part one: " + Day25().solvePartOne())
}

class Day25 {

    private val locks: List<Map<Int, Int>>
    private val keys: List<Map<Int, Int>>

    init {
        val (lockIn, keyIn) = getInputFile(example = false)
            .readText()
            .split("\n\n")
            .partition { it.first() == '#' }
        locks = lockIn.map { it.lines().toPointGrid().filterValues { it == '#' }.keys.groupingBy { it.x }.eachCount() }
        keys = keyIn.map { it.lines().toPointGrid().filterValues { it == '#' }.keys.groupingBy { it.x }.eachCount() }
    }

    fun solvePartOne(): Int {
        return locks.sumOf { lock ->
            keys.count { key ->
                lock.entries.all { (x, c) ->
                    key.getValue(x) + c <= 7
                }
            }
        }
    }
}
