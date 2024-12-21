package y2022.day18

import utils.Point3D
import utils.getInputFile

fun main() {
    println("Part one: ${Day18.solvePartOne()}")
    println("Part two: ${Day18.solvePartTwo()}")
}

object Day18 {

    private val input =
        getInputFile()
            .readLines()
            .map {
                val (x, y, z) = it.split(",", limit = 3)
                Point3D(x.toInt(), y.toInt(), z.toInt())
            }


    fun solvePartOne(): Long {
        return input.foldRight(0) { square, acc ->
            acc + square.getNeighbours().count { it !in input }
        }
    }

    fun solvePartTwo(): Long {
        val xRange = input.minOf(Point3D::x)..input.maxOf(Point3D::x)
        val yRange = input.minOf(Point3D::y)..input.maxOf(Point3D::y)
        val zRange = input.minOf(Point3D::z)..input.maxOf(Point3D::z)

        val isExternal = isExternal@{ point: Point3D ->
            val stack = ArrayDeque(listOf(point))
            val visited = mutableSetOf(point)

            while (stack.isNotEmpty()) {
                val current = stack.removeFirst()
                if (current in input) continue

                if (current.x !in xRange || current.y !in yRange || current.z !in zRange) return@isExternal true // Managed to get outside the bounds, face is externally accessible

                current.getNeighbours()
                    .filter { it !in visited }
                    .forEach {
                        visited += it
                        stack += it
                    }
            }

            false
        }

        return input.foldRight(0) { square, acc ->
            acc + square.getNeighbours().count(isExternal)
        }
    }
}
