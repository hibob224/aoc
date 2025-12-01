package y2015.day13

import template.Puzzle
import template.solve
import utils.allPermutations

fun main() = solve { Day13() }

class Day13 : Puzzle<Int, Int>(2015, 13, example = false) {

    private val inpRegex = """^(\S*).*(gain|lose)\s(\d+).*to\s(.*)\.$""".toRegex()
    override val input = rawInput
        .lines()
        .associate {
            val (a, t, v, b) = inpRegex.find(it)!!.destructured
            val value = if (t == "gain") v.toInt() else -v.toInt()
            (a to b) to value
        }.withDefault { 0 }
    private val allNames = input.keys.flatMap { it.toList() }.toSet()

    override fun solvePartOne(): Int = highestScore(allNames)

    override fun solvePartTwo(): Int = highestScore(allNames + "Me")

    private fun highestScore(names: Set<String>): Int =
        allPermutations(names)
            .maxOf {
                it.drop(1).fold(it.first() to score(it.first(), it.last())) { acc, s ->
                    s to acc.second + score(acc.first, s)
                }.second
            }

    private fun score(a: String, b: String): Int = input.getValue(a to b) + input.getValue(b to a)
}
