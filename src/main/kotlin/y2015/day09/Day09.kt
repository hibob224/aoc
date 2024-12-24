package y2015.day09

import utils.allPermutations
import utils.getInputFile

fun main() {
    val day = Day09()
    println("Part one: " + day.solvePartOne())
    println("Part two: " + day.solvePartTwo())
}

class Day09 {

    private val parseRegex = """^(.+) to (.+) = (\d+)$""".toRegex()
    private val input =
        getInputFile(example = false)
            .readLines()
            .associate {
                val (a, b, d) = parseRegex.find(it)!!.destructured
                (a to b) to d.toLong()
            }
    private val routes =
        allPermutations(input.keys.flatMap { it.toList() }.toSet())
            .map { cityOrder ->
                cityOrder.drop(1).fold(cityOrder.first() to 0L) { (lastCity, accDist), nextCity ->
                    val dist = input[lastCity to nextCity] ?: input[nextCity to lastCity]!!
                    nextCity to accDist + dist
                }
            }

    fun solvePartOne(): Long = routes.minOf { it.second }

    fun solvePartTwo(): Long = routes.maxOf { it.second }
}
