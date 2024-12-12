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
                        println("""
                            Point: $point,
                            GroupNeighbours: $sameGroup,
                            Boundary: $boundary,
                        """.trimIndent())
                        addAll(sameGroup.filter { it !in visited })
                        expanse.addAll(sameGroup.filter { it !in visited })
                        visited.addAll(sameGroup)
                        fenceCount += boundary.size
                    }
                }.size
                groups.add(Triple(group, area, fenceCount))
            }
        }

        println(groups)

        return groups.sumOf { (_, area, fences) -> area * fences }
    }

    fun solvePartTwo(): Long {
        return 0
    }
}
