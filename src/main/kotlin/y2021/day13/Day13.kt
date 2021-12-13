package y2021.day13

import utils.Point
import java.io.File

fun main() {
    println("Part one: ${Day13.solvePartOne()}")
    println("Part two: ${Day13.solvePartTwo()}")
}

object Day13 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private fun parseInput(): List<String> =
        File("src/main/kotlin/$directory/input.txt").readLines()

    fun solvePartOne(): Int {
        val input = parseInput()
        val paper = mutableListOf<Point>()
        val folds = mutableListOf<Fold>()

        input.forEach {
            if (it.startsWith("fold")) {
                val foldOnY = "y" in it
                val foldPoint = it.substringAfter('=').toInt()
                folds += Fold(foldOnY, foldPoint)
            } else if ("," in it) {
                val (x, y) = it.split(",", limit = 2)
                paper += Point(x.toInt(), y.toInt())
            }
        }

        foldPaper(paper, folds.first())

        return paper.size
    }

    fun solvePartTwo(): List<Point> {
        val input = parseInput()
        val paper = mutableListOf<Point>()
        val folds = mutableListOf<Fold>()

        input.forEach {
            if (it.startsWith("fold")) {
                val foldOnY = "y" in it
                val foldPoint = it.substringAfter('=').toInt()
                folds += Fold(foldOnY, foldPoint)
            } else if ("," in it) {
                val (x, y) = it.split(",", limit = 2)
                paper += Point(x.toInt(), y.toInt())
            }
        }

        folds.forEach { foldPaper(paper, it) }

        val out = File("src/main/kotlin/$directory/out.txt")
        out.delete()
        out.writer().use {
            repeat(100) { y ->
                var line = ""
                repeat(100) { x ->
                    val hasPoint = Point(x, y) in paper
                    line += if (hasPoint) "#" else "."
                }
                it.appendLine(line)
            }
        }

        return paper
    }

    private fun foldPaper(paper: MutableList<Point>, fold: Fold) {
        if (fold.foldOnY) {
            val pointsBelowFold = paper.filter { it.y > fold.foldPoint }
            paper.removeAll(pointsBelowFold)
            pointsBelowFold.forEach {
                val diff = it.y - fold.foldPoint
                val newPos = fold.foldPoint - diff
                val newPoint = it.copy(y = newPos)
                if (newPoint !in paper) {
                    paper += newPoint
                }
            }
        } else {
            val pointsBelowFold = paper.filter { it.x > fold.foldPoint }
            paper.removeAll(pointsBelowFold)
            pointsBelowFold.forEach {
                val diff = it.x - fold.foldPoint
                val newPos = fold.foldPoint - diff
                val newPoint = it.copy(x = newPos)
                if (newPoint !in paper) {
                    paper += newPoint
                }
            }
        }
    }
}

data class Fold(val foldOnY: Boolean, val foldPoint: Int)