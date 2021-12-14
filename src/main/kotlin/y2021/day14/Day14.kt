package y2021.day14

import java.io.File

fun main() {
    println("Part one: ${Day14.solvePartOne()}")
    println("Part two: ${Day14.solvePartTwo()}")
}

object Day14 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private fun parseInput(): List<String> =
        File("src/main/kotlin/$directory/input.txt").readLines()

    fun solvePartOne(): Int {
        val input = parseInput()

        var template = input.first()
        val rules = mutableMapOf<Regex, String>()
        val regex = """^([A-Z]{2}) -> ([A-Z])${'$'}""".toRegex()

        (2..input.lastIndex).forEach { index ->
            val (_, a, b) = regex.find(input[index])!!.groupValues
            rules["(?=($a))".toRegex()] = b
        }

        repeat(10) {
            val insertions = mutableMapOf<Int, String>()

            rules.forEach { (key, value) ->
                key.findAll(template).forEach {
                    insertions[it.range.first.inc()] = value
                }
            }

            insertions.entries
                .sortedBy { it.key }
                .forEachIndexed { index, entry ->
                    template = template.insert(entry.key + index, entry.value)
                }
        }

        val counts = template.groupingBy { it }.eachCount()

        return counts.maxByOrNull { it.value }!!.value - counts.minByOrNull { it.value }!!.value
    }

    fun solvePartTwo(): Int {
        return -1
    }

    fun String.insert(index: Int, string: String): String {
        return this.substring(0, index) + string + this.substring(index, this.length)
    }
}