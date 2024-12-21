package y2024.day20

import kotlinx.coroutines.*
import utils.getInputFile
import utils.neighboursInRadius
import utils.shortestPath
import utils.toPointGrid

fun main() {
    runBlocking {
        println("Part one: " + Day20.solvePartOne())
        println("Part two: " + Day20.solvePartTwo())
    }
}

object Day20 {

    private val input = getInputFile()
        .readLines()
        .toPointGrid()

    suspend fun solvePartOne(): Int = shortcuts(maxShortcutLength = 2)

    // This is pretty slow, but we do what we need to do
    suspend fun solvePartTwo(): Int = shortcuts(maxShortcutLength = 20)

    private suspend fun shortcuts(maxShortcutLength: Int): Int = withContext(Dispatchers.IO) {
        val start = input.entries.find { it.value == 'S' }!!.key
        val target = input.entries.find { it.value == 'E' }!!.key

        val (path, _) = input.shortestPath(start, target)

        val biggestShortcut = path
            .map { p ->
                async {
                    val pIndex = path.indexOf(p).inc()
                    p.neighboursInRadius(maxShortcutLength)
                        .mapNotNull {
                            val ind = if (it == target) path.size + 1 else path.indexOf(it).takeIf { it != -1 }?.inc() ?: return@mapNotNull null
                            val diff = ind - pIndex - p.manhattan(it)
                            Triple(p, it, diff)
                        }
                        .filterNot { it.third <= 0 }
                }
            }.awaitAll().flatten()

        return@withContext biggestShortcut.filter { it.third >= 100 }.size
    }
}
