package y2015.day02

import utils.combinations
import utils.getInputFile

fun main() {
    println("Part one: " + Day02.solvePartOne())
    println("Part two: " + Day02.solvePartTwo())
}

object Day02 {

    private val input = getInputFile(this::class.java.packageName)
        .readLines()
        .map { it.split("x").map(String::toInt) }

    fun solvePartOne(): Int = input.fold(0) { acc, dimens ->
        acc + dimens.combinations(2)
            .map { it[0] * it[1] }
            .let { it.sum() * 2 + it.min() }
    }

    fun solvePartTwo(): Int = input.fold(0) { acc, dimens ->
        acc + dimens.combinations(2)
            .map { it[0] + it[1] }
            .let { it.min() * 2 + dimens.fold(1) { acc, i -> acc * i} }
    }
}
