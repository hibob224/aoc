package y2023.day08

import utils.getInputFile

fun main() {
    println("Part one: ${Day08.solvePartOne()}")
    println("Part two: ${Day08.solvePartTwo()}")
}

object Day08 {

    private val locations: Map<String, Pair<String, String>>
    private val instructions: List<Char>

    init {
        val input = getInputFile(this::class.java.packageName, example = false).readLines()
        instructions = input[0].toList()
        val regex = """^([A-Z]{3}) = \(([A-Z]{3}), ([A-Z]{3})\)${'$'}""".toRegex()
        locations = input.drop(n = 2)
            .associate {
                val (current, left, right) = regex.find(it)!!.destructured
                current to (left to right)
            }
    }

    fun solvePartOne(): Long {
        val target = "ZZZ"
        var current = "AAA"
        var index = 0L

        while (target != current) {
            val left = instructions[(index % instructions.size).toInt()] == 'L'
            current = if (left) locations[current]!!.first else locations[current]!!.second
            index++
        }

        return index.toLong()
    }

    fun solvePartTwo(): Long = 0
}
