package y2025.day07

import template.Puzzle
import template.solve
import utils.Point
import utils.toPointGrid

fun main() = solve { Day07() }

class Day07 : Puzzle<Long, Long>(2025, 7, example = false) {

    override val input = rawInput.lines().toPointGrid()

    override fun solvePartOne(): Long {
        val grid = input.toMutableMap()
        val start = grid.entries.find { it.value == 'S' }!!.key
        grid[start.s] = '|'
        val split = mutableListOf<Point>()

        for (y in 1..grid.keys.maxOf { it.y }) {
            for (x in 0..grid.keys.maxOf { it.x }) {
                val coord = Point(x, y)
                if (grid[coord] == '|') {
                    val down = grid[coord.s]
                    if (down == '^') {
                        split += coord.s
                        grid[coord.s.w] = grid[coord.s.w].takeIf { it == '^' } ?: '|'
                        grid[coord.s.e] = grid[coord.s.e].takeIf { it == '^' } ?: '|'
                    } else {
                        grid[coord.s] = '|'
                    }
                }
            }
        }

        return split.size.toLong()
    }

    override fun solvePartTwo(): Long {
        val grid = input
        val start = grid.entries.find { it.value == 'S' }!!.key
        var front = mapOf<Point, Long>(start to 1)

        for (y in 1..grid.keys.maxOf { it.y }) {
            val newFront = mutableMapOf<Point, Long>()
            front.forEach { (pos, value) ->
                val down = grid[pos.s]
                if (down == '^') {
                    newFront[pos.s.w] = newFront.getOrDefault(pos.s.w, 0) + value
                    newFront[pos.s.e] = newFront.getOrDefault(pos.s.e, 0) + value
                } else {
                    newFront[pos.s] = newFront.getOrDefault(pos.s, 0) + value
                }
            }
            front = newFront
        }

        return front.values.sum()
    }
}
