package y2025.day09

import template.Puzzle
import template.solve
import utils.Point
import utils.combinations
import java.awt.Polygon
import java.awt.geom.Rectangle2D
import kotlin.math.absoluteValue

fun main() = solve { Day09() }

class Day09 : Puzzle<Long, Long>(2025, 9, example = false) {

    override val input = rawInput
        .lines()
        .map { it.split(",") }
        .map { (x, y) -> Point(x.toInt(), y.toInt()) }

    override fun solvePartOne(): Long =
        input.combinations(2)
            .maxOf { (a, b) ->
                (a.x - b.x + 1).toLong() * (a.y - b.y + 1).toLong()
            }

    override fun solvePartTwo(): Long {
        val polygon = Polygon()
        input.forEach { (x, y) ->
            polygon.addPoint(x, y)
        }
        val rects = input
            .combinations(2)
            .map { (a, b) ->
                Rectangle2D.Double(
                    minOf(a.x, b.x).toDouble(),
                    minOf(a.y, b.y).toDouble(),
                    (b.x - a.x).absoluteValue.toDouble(),
                    (b.y - a.y).absoluteValue.toDouble(),
                )
            }
        return rects
            .filter { it in polygon }
            .maxOf { it.width.inc() * it.height.inc() }
            .toLong()
    }
}
