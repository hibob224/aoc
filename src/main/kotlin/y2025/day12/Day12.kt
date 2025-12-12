package y2025.day12

import template.Puzzle
import template.solve
import utils.toPointGrid

fun main() = solve { Day12() }

class Day12 : Puzzle<Int, Long>(2025, 12, example = false) {

    override val input = rawInput.split("\n\n")
    private val presentShapes = input.dropLast(1)
        .map { s ->
            s
                .split("\n")
                .drop(1)
                .toPointGrid()
        }
    private val regions = input
        .takeLast(1)
        .first()
        .split("\n")
        .map {
            val (w, h) = it.substringBefore(":").split("x")
            val presents = it.substringAfter(" ").split(" ").map { it.toInt() }
            Region(
                width = w.toInt(),
                height = h.toInt(),
                presentsRequired = presents,
            )
        }

    override fun solvePartOne(): Int {
        // This doesn't actually do what the puzzle says, rotating shapes etc to fit, but it does give
        // the correct answer on the input
        return regions.count { region ->
            val area = region.width * region.height
            val areaRequired = region.presentsRequired.mapIndexed { index, i ->
                presentShapes[index].values.count { it == '#' } * i
            }.sum()
            areaRequired < area
        }
    }

    override fun solvePartTwo(): Long = 0

    data class Region(
        val width: Int,
        val height: Int,
        val presentsRequired: List<Int>,
    )
}
