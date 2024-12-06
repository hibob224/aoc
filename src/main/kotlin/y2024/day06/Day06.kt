package y2024.day06

import utils.Point
import utils.getInputFile
import utils.toPointGrid

fun main() {
    println("Part one: " + Day06.solvePartOne())
    println("Part two: " + Day06.solvePartTwo())
}

object Day06 {

    private lateinit var guardStartPos: Point
    private val input = getInputFile(this::class.java.packageName, example = false)
        .readLines()
        .toPointGrid { pos, c ->
            if (c == '^') guardStartPos = pos
            c
        }
    private val width = input.maxOf { it.key.x }
    private val height = input.maxOf { it.key.y }

    fun solvePartOne(): Int = patrol(input)!!.size

    fun solvePartTwo(): Int {
        // Take all the originally visited positions, try placing an obstacle in that position and run through the patrol
        return patrol(input)!!
            .count { pos ->
                // If patrol returns 'null', the guard got stuck in a loop
                patrol(input.toMutableMap().apply { put(pos, '#') }) == null
            }
    }

    private fun patrol(grid: Map<Point, Char>): Set<Point>? {
        var guardPos = guardStartPos
        var direction = north
        val visitedPositionsWithDirection = mutableSetOf<Pair<Point, Point>>()

        do {
            if ((guardPos to direction) in visitedPositionsWithDirection) {
                // We've already visited this position before, facing the same direction, we're stuck in a loop
                return null
            }
            visitedPositionsWithDirection += guardPos to direction

            val newPos = guardPos + direction
            when (grid[newPos]) {
                '#' -> direction = turn(direction) // Blocked, turn 90 degrees
                else -> guardPos = newPos
            }
        } while (guardPos.x in 0..width && guardPos.y in 0..height) // Check the guard is still within bounds

        // Guard left the bounds of the grid, return all uniquely visited positions
        return visitedPositionsWithDirection.map(Pair<Point, Point>::first).toSet()
    }

    private val north = Point(0, -1)
    private val south = Point(0, 1)
    private val east = Point(1, 0)
    private val west = Point(-1, 0)

    private fun turn(direction: Point): Point = when (direction) {
        north -> east
        east -> south
        south -> west
        west -> north
        else -> error("Invalid direction")
    }
}
