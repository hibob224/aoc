package y2022.day11

import utils.getInputFile
import utils.isMultipleOf

fun main() {
    println("Part one: ${Day11.solvePartOne()}")
    println("Part two: ${Day11.solvePartTwo()}")
}

object Day11 {

    private val opRegex = """Operation: new = (.+) ([*+]) (.*)""".toRegex()
    private val input = getInputFile(this::class.java.packageName)
        .readText()
        .split("\n\n")
        .map { it.split("\n") }
        .map {
            val startingItems = it[1].split(": ")[1].split(", ").map(String::toLong)
            val (_, a, operator, b) = opRegex.find(it[2])!!.groupValues
            val operation = when (operator) {
                "*" -> Operation.Multiply(a, b)
                "+" -> Operation.Add(a, b)
                else -> error("Unknown operation")
            }
            val test = it[3].split("by ")[1].toLong()
            val targTrue = it[4].split("monkey ")[1].toInt()
            val targFalse = it[5].split("monkey ")[1].toInt()
            Monkey(startingItems, operation, test, targTrue to targFalse)
        }

    fun solvePartOne(): Long = solve(20) { it / 3 }

    fun solvePartTwo(): Long {
        val mod = input.map(Monkey::test).reduce { acc, l -> acc * l }
        return solve(10_000) { it % mod }
    }

    private fun solve(rounds: Int, postOp: (Long) -> Long): Long {
        val monkeyItems = input.map(Monkey::startingItems).toMutableList()
        val monkeyInspections = Array(input.size) { 0L }

        repeat(rounds) {
            input.forEachIndexed { index, monkey ->
                val items = monkeyItems[index]
                items.forEach { item ->
                    val newWorry = postOp(monkey.operation(item))
                    val targetMonkey = if (newWorry.isMultipleOf(monkey.test)) monkey.targetMonkeys.first else monkey.targetMonkeys.second
                    monkeyItems[targetMonkey] = monkeyItems[targetMonkey] + newWorry
                }
                monkeyInspections[index] += items.size.toLong()
                monkeyItems[index] = emptyList()
            }
        }

        return monkeyInspections.sortedArrayDescending().take(2).reduce { acc, i -> acc * i }
    }

    data class Monkey(
        val startingItems: List<Long>,
        val operation: Operation,
        val test: Long,
        val targetMonkeys: Pair<Int, Int>
    )

    sealed class Operation(val a: String, val b: String) {
        class Add(a: String, b: String) : Operation(a, b) {
            override fun perform(a: Long, b: Long): Long = a + b
        }

        class Multiply(a: String, b: String) : Operation(a, b) {
            override fun perform(a: Long, b: Long): Long = a * b
        }

        operator fun invoke(old: Long): Long {
            val a = if (this.a == "old") old else this.a.toLong()
            val b = if (this.b == "old") old else this.b.toLong()
            return perform(a, b)
        }

        abstract fun perform(a: Long, b: Long): Long
    }
}
