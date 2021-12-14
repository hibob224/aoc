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

    fun solvePartTwo(): Long {
        val input = parseInput()

        val template = input.first()
        var counts: Map<String, Long>
        val rules = mutableMapOf<String, List<String>>()
        val regex = """^([A-Z]{2}) -> ([A-Z])${'$'}""".toRegex()

        (2..input.lastIndex).forEach { index ->
            val (_, a, b) = regex.find(input[index])!!.groupValues
            rules[a] = listOf("${a.first()}$b", "$b${a[1]}")
        }

        counts = template
            .windowed(2)
            .groupingBy { it }
            .eachCount()
            .map { it.key to it.value.toLong() }
            .toMap()

        repeat(40) {
            val newCounts = mutableMapOf<String, Long>()
            counts.forEach { (key, value) ->
                rules[key]?.let { rule ->
                    rule.forEach {
                        newCounts.putIfAbsent(it, 0L)
                        newCounts[it] = newCounts[it]!! + value
                    }
                } ?: run {
                    newCounts.putIfAbsent(key, 0L)
                    newCounts[key] = newCounts[key]!! + value
                }
            }
            counts = newCounts
        }

        val letterCount = mutableMapOf<Char, Long>()

        counts.forEach { (key, value) ->
            key.toCharArray().forEach {
                letterCount.putIfAbsent(it, 0L)
                letterCount[it] = letterCount[it]!! + value
            }
        }

        return ((letterCount.maxByOrNull { it.value }!!.value - letterCount.minByOrNull { it.value }!!.value) / 2)
    }

    fun String.insert(index: Int, string: String): String {
        return this.substring(0, index) + string + this.substring(index, this.length)
    }
}