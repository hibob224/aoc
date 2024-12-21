package y2019.day10

import utils.Point
import utils.getInputFile

fun main() {
    println("Part one: ${Day10.solvePartOne()}")
    println("Part two: ${Day10.solvePartTwo()}")
}

object Day10 {

    private fun parseInput(): List<Point> {
        val list = mutableListOf<Point>()
        getInputFile().readLines().forEachIndexed { y, s ->
            s.forEachIndexed { x, c ->
                if (c == '#') {
                    list += Point(x, y)
                }
            }
        }
        return list
    }

    fun solvePartOne(): String {
        val data = parseInput()
        return data.map { check ->
            check to (data - check).groupBy { it.angle(check) }.size
        }.maxBy { it.second }.second.toString()
    }

    fun solvePartTwo(): String {
        val data = parseInput()
        val laser = data.map { check ->
            check to (data - check)
                .sortedBy { it.manhattan(check) } // Sort by manhatten so asteroids in the same row are obliterated in distance order
                .groupBy { it.degrees(check) } // Group asteroids by their degrees (0-360) from the laser so we can go clockwise easily
                .toMutableMap()
        }.maxBy { it.second.size }
        val obliterated = mutableListOf<Point>()

        while (laser.second.values.flatten().isNotEmpty()) { // Loop till we've destroyed EVERYTHING, could stop at 200 but there's not too many anyway
            // Loop from 0 degrees until 360, destroying one asteroid per degree
            laser.second.filterNot { it.value.isEmpty() }.keys.sorted().forEach {
                obliterated.add(laser.second[it]!!.first())
                laser.second[it] = laser.second[it]!!.subList(1, laser.second[it]!!.size)
            }
        }

        return obliterated[199].let { it.x.times(100).plus(it.y) }.toString()
    }
}
