package y2024.day20

import utils.getInputFile
import utils.neighboursInRadius
import utils.shortestPath
import utils.toPointGrid

fun main() {
    println("Part one: " + Day20.solvePartOne())
    println("Part two: " + Day20.solvePartTwo())
}

object Day20 {

    private val input = getInputFile(this::class.java.packageName, example = false)
        .readLines()
        .toPointGrid()

    fun solvePartOne(): Int {
        val start = input.entries.find { it.value == 'S' }!!.key
        val target = input.entries.find { it.value == 'E' }!!.key

        val (path, dist) = input.shortestPath(start, target)

        val maxShortcut = 2
        val biggestShortcut = path.flatMap { p ->
            val pIndex = path.indexOf(p).inc()
            p.neighboursInRadius(maxShortcut)
                .mapNotNull {
                    val ind = if (it == target) path.size + 1 else path.indexOf(it).takeIf { it != -1 }?.inc() ?: return@mapNotNull null
                    val diff = ind - pIndex - p.manhattan(it)
                    Triple(p, it, diff)
                }
                .filterNot { it.third <= 0 }
        }

        return biggestShortcut.filter { it.third >= 100 }.size
    }

    fun solvePartTwo(): Int {
        val start = input.entries.find { it.value == 'S' }!!.key
        val target = input.entries.find { it.value == 'E' }!!.key

        val (path, dist) = input.shortestPath(start, target)

        val maxShortcut = 20
        val biggestShortcut = path.flatMap { p ->
            val pIndex = path.indexOf(p).inc()
            p.neighboursInRadius(maxShortcut)
                .mapNotNull {
                    val ind = if (it == target) path.size + 1 else path.indexOf(it).takeIf { it != -1 }?.inc() ?: return@mapNotNull null
                    val diff = ind - pIndex - p.manhattan(it)
                    Triple(p, it, diff)
                }
                .filterNot { it.third <= 0 }
        }

        return biggestShortcut.filter { it.third >= 100 }.size
    }
}
