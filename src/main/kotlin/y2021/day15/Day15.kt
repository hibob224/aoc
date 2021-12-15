package y2021.day15

import utils.Point
import java.io.File

fun main() {
    println("Part one: ${Day15.solvePartOne()}")
    println("Part two: ${Day15.solvePartTwo()}")
}

object Day15 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private fun parseInput(): List<String> =
        File("src/main/kotlin/$directory/input.txt").readLines()

    fun solvePartOne(): Int {
        val input = parseInput()
        val grid = mutableMapOf<Point, Int>()

        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                grid[Point(x, y)] = c.toString().toInt()
            }
        }

        val start = Point(0, 0)
        val target = Point(x = input.first().lastIndex, y = input.lastIndex)
        val openSet = mutableSetOf(start)
        val cameFrom = mutableMapOf<Point, Point>()
        val gScore = mutableMapOf(start to 0)
        val fScore = mutableMapOf(start to Integer.MAX_VALUE)

        while (openSet.isNotEmpty()) {
            val current = openSet.minByOrNull { fScore[it]!! }!!
            if (current == target) {
                val path = mutableListOf(current)
                var next = current
                while (next in cameFrom.keys) {
                    next = cameFrom[next]!!
                    path.add(0, next)
                }
                return path.subList(fromIndex = 1, toIndex = path.size).sumBy { grid[it]!! }
            }

            openSet.remove(current)

            current.getNeighbours()
                .filter { it.x in 0..target.x && it.y in 0..target.y }
                .forEach { neighbour ->
                    val tentativeGScore = gScore[current]!! + grid[neighbour]!!
                    if (tentativeGScore < gScore.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                        cameFrom[neighbour] = current
                        gScore[neighbour] = tentativeGScore
                        fScore[neighbour] = tentativeGScore + neighbour.manhattan(target)
                        if (neighbour !in openSet) {
                            openSet += neighbour
                        }
                    }
                }

        }

        throw IllegalStateException("How")
    }

    fun solvePartTwo(): Int {
        val input = parseInput()
        val grid = mutableMapOf<Point, Int>()
        val tileWidth = input.first().length
        val tileHeight = input.size

        val tempGrid = input.map { row ->
            row.map { it.toString().toInt() }.toMutableList()
        }.toMutableList()

        // Enlarge the grid...
        val newWidth = tileWidth * 5
        val newHeight = tileWidth * 5
        val newerGrid = List(newHeight) { List(newWidth) { 0 }.toMutableList() }

        (0 until newHeight).forEach { y ->
            (0 until newWidth).forEach { x ->
                val checkX = x % tileWidth
                val checkY = y % tileHeight
                val addX = x / tileWidth
                val addY = y / tileHeight
                newerGrid[y][x] = if (tempGrid[checkY][checkX] + addX + addY > 9) {
                    tempGrid[checkY][checkX] + addX + addY - 9
                } else {
                    tempGrid[checkY][checkX] + addX + addY
                }
            }
        }

        newerGrid.forEachIndexed { y, row ->
            row.forEachIndexed { x, i ->
                grid[Point(x, y)] = i
            }
        }

        val start = Point(0, 0)
        val target = Point(x = grid.maxOf { it.key.x }, y = grid.maxOf { it.key.y })
        val openSet = mutableSetOf(start)
        val cameFrom = mutableMapOf<Point, Point>()
        val gScore = mutableMapOf(start to 0)
        val fScore = mutableMapOf(start to Integer.MAX_VALUE)

        while (openSet.isNotEmpty()) {
            val current = openSet.minByOrNull { fScore[it]!! }!!
            if (current == target) {
                val path = mutableListOf(current)
                var next = current
                while (next in cameFrom.keys) {
                    next = cameFrom[next]!!
                    path.add(0, next)
                }
                return path.subList(fromIndex = 1, toIndex = path.size).sumBy { grid[it]!! }
            }

            openSet.remove(current)

            current.getNeighbours()
                .filter { it.x in 0..target.x && it.y in 0..target.y }
                .forEach { neighbour ->
                    val tentativeGScore = gScore[current]!! + grid[neighbour]!!
                    if (tentativeGScore < gScore.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                        cameFrom[neighbour] = current
                        gScore[neighbour] = tentativeGScore
                        fScore[neighbour] = tentativeGScore + neighbour.manhattan(target)
                        if (neighbour !in openSet) {
                            openSet += neighbour
                        }
                    }
                }

        }

        throw IllegalStateException("How")
    }
}