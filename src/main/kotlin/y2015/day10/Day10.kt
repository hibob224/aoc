package y2015.day10

import template.Puzzle
import template.solve

fun main() = solve { Day10() }

class Day10 : Puzzle<Int, Int>(2015, 10) {

    private val regex = """(\d)\1*""".toRegex()
    override val input = rawInput

    override fun solvePartOne(): Int = generateSequence(input, ::lookAndSee).elementAt(40).length

    override fun solvePartTwo(): Int = generateSequence(input, ::lookAndSee).elementAt(50).length

    private fun lookAndSee(inp: String): String {
        val matches = regex.findAll(inp)
        return matches.joinToString("") { "${it.groups.first()!!.value.length}${it.groups[1]!!.value}" }
    }
}
