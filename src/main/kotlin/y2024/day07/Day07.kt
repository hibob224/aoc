package y2024.day07

import utils.getInputFile

fun main() {
    println("Part one: " + Day07.solvePartOne())
    println("Part two: " + Day07.solvePartTwo())
}

private fun Long.concat(other: Long): Long = "$this$other".toLong()

object Day07 {

    private val input = getInputFile(this::class.java.packageName, example = false)
        .readLines()
        .map {
            val (targ, nums) = it.split(": ", limit = 2)
            targ.toLong() to nums.split(" ").map(String::toLong)
        }

    fun solvePartOne(): Long = solve(listOf(Long::plus, Long::times))

    fun solvePartTwo(): Long = solve(listOf(Long::plus, Long::times, Long::concat))

    private fun solve(operations: List<Long.(Long) -> Long>): Long =
        input
            .filter { eval(it.second.first(), it.first, it.second, 1, operations) }
            .sumOf { it.first }

    private fun eval(
        acc: Long,
        target: Long,
        nums: List<Long>,
        index: Int,
        operations: List<Long.(Long) -> Long>,
    ): Boolean {
        if (index > nums.lastIndex) return acc == target
        if (acc > target) return false
        return operations
            .map { it(acc, nums[index]) }
            .any { eval(it, target, nums, index.inc(), operations) }
    }
}
