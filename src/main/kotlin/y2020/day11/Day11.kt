package y2020.day11

import java.io.File

fun main() {
    println("Part one: ${Day11.solvePartOne()}")
    println("Part two: ${Day11.solvePartTwo()}")
}

object Day11 {

    private const val SEAT = 'L'
    private const val OCCUPIED = '#'
    private const val STAIR = '.'

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val input: List<MutableList<Char>> =
        File("src/main/kotlin/$directory/test.txt")
            .readLines()
            .map { it.toCharArray().toMutableList() }

    fun solvePartOne(): String {
        var grid: List<MutableList<Char>>
        val newGrid = input.map(List<Char>::toMutableList)

        do {
            grid = newGrid.map { it.toMutableList() }

            repeat(grid.first().size) { x ->
                repeat(grid.size) yloop@ { y ->
                    val current = grid[y][x]
                    if (current == STAIR) return@yloop

                    val surrounding = listOfNotNull(
                        grid.getOrNull(y.dec())?.getOrNull(x.dec()),
                        grid.getOrNull(y.dec())?.getOrNull(x),
                        grid.getOrNull(y.dec())?.getOrNull(x.inc()),
                        grid.getOrNull(y)?.getOrNull(x.dec()),
                        grid.getOrNull(y)?.getOrNull(x.inc()),
                        grid.getOrNull(y.inc())?.getOrNull(x.dec()),
                        grid.getOrNull(y.inc())?.getOrNull(x),
                        grid.getOrNull(y.inc())?.getOrNull(x.inc())
                    )

                    if (current == OCCUPIED) {
                        newGrid[y][x] = if (surrounding.count { it == OCCUPIED } >= 4) {
                            SEAT
                        } else {
                            OCCUPIED
                        }
                    } else {
                        newGrid[y][x] = if (surrounding.none { it == OCCUPIED }) {
                            OCCUPIED
                        } else {
                            SEAT
                        }
                    }
                }
            }

        } while (grid != newGrid)

        return grid.flatten().count { it == '#' }.toString()
    }

    fun solvePartTwo(): String {
        var grid: List<MutableList<Char>>
        val newGrid = input.map(List<Char>::toMutableList)

        do {
            grid = newGrid.map(List<Char>::toMutableList)

            repeat(grid.first().size) { x ->
                repeat(grid.size) yloop@{ y ->
                    val current = grid[y][x]
                    if (current == STAIR) return@yloop


                }
            }
        } while (grid != newGrid)

        return grid.flatten().count { it == '#' }.toString()
    }
}