package y2025.day04

import template.Puzzle
import template.solve
import utils.toPointGrid

fun main() = solve { Day04() }

class Day04 : Puzzle<Int, Int>(2025, 4, example = false) {

    override val input = rawInput.split("\n").toPointGrid()
    private val paper = input
        .filter { it.value == '@' }
        .toMap()
        .keys
        .associateWith { it.getNeighbours(diagonal = true) }

    override fun solvePartOne(): Int {
        return paper.filter { (_, adjacent) ->
            adjacent.count { it in paper.keys } < 4
        }.size
    }

    override fun solvePartTwo(): Int {
        var paper = paper
        var paperCount: Int

        do {
            paperCount = paper.size
            paper = paper.filterNot { (_, adjacent) ->
                adjacent.count { it in paper.keys } < 4
            }
        } while (paper.size != paperCount)

        return this.paper.size - paper.size
    }
}
