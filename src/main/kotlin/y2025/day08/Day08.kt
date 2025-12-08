package y2025.day08

import template.Puzzle
import template.solve
import utils.Point3D
import utils.product

fun main() = solve { Day08() }

class Day08 : Puzzle<Long, Long>(2025, 8, example = false) {

    override val input = rawInput
        .lines()
        .map {
            Point3D(it.split(",").map(String::toInt))
        }
    private val dists = input.mapIndexed { index, point ->
        input.drop(index.inc()).associate { point2 ->
            val dist = point.euclidean(point2)
            (point to point2) to dist
        }
    }.flatMap { it.entries }.sortedBy { it.value }.map { it.key }

    override fun solvePartOne(): Long {
        val circuits = mutableListOf<Set<Point3D>>()

        dists.take(1000).forEach { (a, b) ->
            val cA = circuits.find { a in it } ?: setOf(a)
            val cB = circuits.find { b in it } ?: setOf(b)
            circuits -= cA
            circuits -= cB
            circuits.add(cA + cB)
        }

        return circuits.sortedByDescending { it.size }.take(3).map { it.size.toLong() }.product()
    }

    override fun solvePartTwo(): Long {
        val circuits = input.map(::setOf).toMutableList()

        dists.forEach { (a, b) ->
            val cA = circuits.find { a in it } ?: setOf(a)
            val cB = circuits.find { b in it } ?: setOf(b)
            circuits -= cA
            circuits -= cB
            circuits.add(cA + cB)
            if (circuits.size == 1) return a.x.toLong() * b.x.toLong()
        }

        error("Failed")
    }
}
