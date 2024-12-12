package y2024.day12

import utils.Point
import utils.getInputFile
import utils.toPointGrid

fun main() {
    println("Part one: " + Day12.solvePartOne())
    println("Part two: " + Day12.solvePartTwo())
}

object Day12 {

    private val input = getInputFile(this::class.java.packageName, example = false)
        .readLines()
        .toPointGrid()
    private val groups = mutableListOf<Set<Pair<Point, Cell>>>()

    init {
        val visited = mutableSetOf<Point>()

        input.forEach { (point, group) ->
            if (point in visited) return@forEach
            visited += point

            val area = buildSet {
                val expanse = mutableSetOf(point)

                while (expanse.isNotEmpty()) {
                    val expansion = expanse.first().also(expanse::remove)
                    val (sameGroup, boundary) = expansion
                        .getNeighbours()
                        .partition { input[it] == group }

                    val cell = Cell(
                        boundN = expansion.n in boundary,
                        boundE = expansion.e in boundary,
                        boundS = expansion.s in boundary,
                        boundW = expansion.w in boundary,
                    )

                    add(expansion to cell)
                    expanse.addAll(sameGroup.filter { it !in visited })
                    visited.addAll(sameGroup)
                }
            }
            groups.add(area)
        }
    }

    fun solvePartOne(): Int = groups.sumOf { points ->
        points.size * points.sumOf { it.second.fenceCount }
    }

    fun solvePartTwo(): Int {
        return groups.sumOf { points ->
            val v = points
                .sortedBy { it.first.y } // Sort in Y order, so it's easier to check for consecutives later
                .groupBy { it.first.x } // Group by X values, then we can count consecutive sections in each X column
            val h = points
                .sortedBy { it.first.x } // Sort in X order
                .groupBy { it.first.y } // Group by Y

            val hSides = h.entries
                .sumOf {
                    // X coord of all horizontal north bounds
                    val nBoundaries = it.value.filter { cell -> cell.second.boundN }.map { cell -> cell.first.x }
                    // X coord of all horizontal south bounds
                    val sBoundaries = it.value.filter { cell -> cell.second.boundS }.map { cell -> cell.first.x }
                    // Find all the consecutive north + south ranges
                    getConsecutiveNumbers(nBoundaries).size + getConsecutiveNumbers(sBoundaries).size
                }
            val vSides = v.entries
                .sumOf {
                    // Y coord of all horizontal east bounds
                    val eBoundaries = it.value.filter { cell -> cell.second.boundE }.map { cell -> cell.first.y }
                    // Y coord of all horizontal west bounds
                    val wBoundaries = it.value.filter { cell -> cell.second.boundW }.map { cell -> cell.first.y }
                    // Find all the consecutive east + west ranges
                    getConsecutiveNumbers(eBoundaries).size + getConsecutiveNumbers(wBoundaries).size
                }

            points.size * (hSides + vSides)
        }
    }

    private fun getConsecutiveNumbers(srcList: List<Int>): List<List<Int>> {
        return srcList.fold(mutableListOf<MutableList<Int>>()) { acc, i ->
            if (acc.isEmpty() || acc.last().last() != i - 1) {
                acc.add(mutableListOf(i))
            } else acc.last().add(i)
            acc
        }
    }

    data class Cell(
        val boundN: Boolean = false,
        val boundE: Boolean = false,
        val boundS: Boolean = false,
        val boundW: Boolean = false,
    ) {
        val fenceCount: Int
            get() = listOf(boundN, boundE, boundS, boundW).count { it }
    }
}
