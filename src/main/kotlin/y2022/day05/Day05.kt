package y2022.day05

import utils.getInputFile

fun main() {
    println("Part one: ${Day05.solvePartOne()}")
    println("Part two: ${Day05.solvePartTwo()}")
}

object Day05 {

    private val initialStacks = mutableMapOf<Int, ArrayDeque<Char>>()
    private val moves = mutableListOf<Triple<Int, Int, Int>>()

    init {
        parseInput()
    }

    private fun parseInput() {
        var (stackInput, moveInput) = getInputFile(this::class.java.packageName)
            .readText()
            .split("\n\n", limit = 2)
            .map { it.split("\n") }

        //region stacks
        stackInput = stackInput.dropLast(1)
        val crateRegex = """\[([A-Z])]""".toRegex()
        stackInput.forEach { row ->
            crateRegex.findAll(row)
                .forEach {
                    val crateIndex = it.range.first / 4
                    initialStacks.getOrPut(crateIndex) { ArrayDeque() }.addLast(it.groups[1]!!.value.first())
                }
        }
        //endregion

        //region moves
        val moveRegex = """^move (\d+) from (\d+) to (\d+)$""".toRegex()
        moveInput.forEach {
            val (_, number, start, end) = moveRegex.find(it)!!.groupValues
            moves.add(Triple(number.toInt(), start.toInt(), end.toInt()))
        }
        //endregion
    }

    fun solvePartOne(): String {
        val stacks = initialStacks.map { it.key to ArrayDeque(it.value) }.toMap()
        moves.forEach {
            val startCrate = stacks[it.second.dec()]!!
            val endCrate = stacks[it.third.dec()]!!
            repeat(it.first) {
                endCrate.addFirst(startCrate.removeFirst())
            }
        }
        return stacks.keys.sorted().joinToString(separator = "", transform = {
            stacks[it]!!.first().toString()
        })
    }

    fun solvePartTwo(): String {
        val stacks = initialStacks.map { it.key to ArrayDeque(it.value) }.toMap()
        moves.forEach {
            val startCrate = stacks[it.second.dec()]!!
            val endCrate = stacks[it.third.dec()]!!
            val movingCrates = mutableListOf<Char>()
            repeat(it.first) {
                movingCrates.add(startCrate.removeFirst())
            }
            movingCrates.reversed().forEach(endCrate::addFirst)
        }
        return stacks.keys.sorted().joinToString(separator = "", transform = {
            stacks[it]!!.first().toString()
        })
    }
}
