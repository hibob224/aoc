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
    private val width = input.maxOf { it.key.x }
    private val height = input.maxOf { it.key.y }

    fun solvePartOne(): Int {
        val visited = mutableSetOf<Point>()
        val groups = mutableListOf<Triple<Char, Int, Int>>()

        (0..height).forEach { y ->
            (0..width).forEach xloop@{ x ->
                val point = Point(x, y)
                val group = input[point]
                if (point in visited || group == null) return@xloop
                visited += point

                var fenceCount = 0
                val area = buildSet {
                    val expanse = mutableSetOf(point)
                    add(point)

                    while (expanse.isNotEmpty()) {
                        val (sameGroup, boundary) = expanse
                            .first()
                            .also(expanse::remove)
                            .getNeighbours()
                            .partition { input[it] == group }
                        addAll(sameGroup.filter { it !in visited })
                        expanse.addAll(sameGroup.filter { it !in visited })
                        visited.addAll(sameGroup)
                        fenceCount += boundary.size
                    }
                }.size
                groups.add(Triple(group, area, fenceCount))
            }
        }

        return groups.sumOf { (_, area, fences) -> area * fences }
    }

    data class Cell(
        val value: Char,
        val boundaryN: Boolean = false,
        val boundaryE: Boolean = false,
        val boundaryS: Boolean = false,
        val boundaryW: Boolean = false,
    )

    fun solvePartTwo(): Int {
        val visited = mutableSetOf<Point>()
        val groups = mutableListOf<Pair<Char, Set<Pair<Point, Cell>>>>()

        input.forEach xloop@{ (point, group) ->
            if (point in visited) return@xloop
            visited += point

            val area = buildSet {
                val expanse = mutableSetOf(point)

                while (expanse.isNotEmpty()) {
                    val expansion = expanse.first().also(expanse::remove)
                    val (sameGroup, boundary) = expansion
                        .getNeighbours()
                        .partition { input[it] == group }

                    val cell = Cell(
                        value = group,
                        boundaryN = expansion.n in boundary,
                        boundaryE = expansion.e in boundary,
                        boundaryS = expansion.s in boundary,
                        boundaryW = expansion.w in boundary,
                    )

                    add(expansion to cell)
                    expanse.addAll(sameGroup.filter { it !in visited })
                    visited.addAll(sameGroup)
                }
            }
            groups.add(group to area)
        }

        return groups.sumOf { (_, points) ->
            val v = points
                .sortedBy { it.first.y }
                .groupBy { it.first.x }
            val h = points
                .sortedBy { it.first.x }
                .groupBy { it.first.y }

            val hSides = h.entries
                .sumOf {
                    getConsecutiveNumbers(it.value.filter { it.second.boundaryN }.map { it.first.x }).size +
                        getConsecutiveNumbers(it.value.filter { it.second.boundaryS }.map { it.first.x }).size
                }
            val vSides = v.entries
                .sumOf {
                    getConsecutiveNumbers(it.value.filter { it.second.boundaryE }.map { it.first.y }).size +
                        getConsecutiveNumbers(it.value.filter { it.second.boundaryW }.map { it.first.y }).size
                }

            points.size * (hSides + vSides)
        }
    }

    fun getConsecutiveNumbers(srcList: List<Int>): List<List<Int>> {
        return srcList.fold(mutableListOf<MutableList<Int>>()) { acc, i ->
            if (acc.isEmpty() || acc.last().last() != i - 1) {
                acc.add(mutableListOf(i))
            } else acc.last().add(i)
            acc
        }
    }
}
