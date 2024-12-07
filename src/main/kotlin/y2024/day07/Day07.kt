package y2024.day07

import utils.getInputFile

fun main() {
    println("Part one: " + Day07.solvePartOne())
    println("Part two: " + Day07.solvePartTwo())
}

object Day07 {

    private val input = getInputFile(this::class.java.packageName, example = false)
        .readLines()
        .map {
            val (targ, nums) = it.split(": ", limit = 2)
            targ.toLong() to nums.split(" ").map(String::toLong)
        }

    fun solvePartOne(): Long {
        return input
            .filter { eval(it.second.first(), it.first, it.second, 1) }
            .sumOf { it.first }
    }

    fun solvePartTwo(): Long {
        return input
            .filter { eval2(it.second.first(), it.first, it.second, 1) }
            .sumOf { it.first }
    }

    private fun eval(acc: Long, target: Long, nums: List<Long>, index: Int): Boolean {
        if (index > nums.lastIndex) return acc == target
        if (acc > target) return false
        return eval(acc + nums[index], target, nums, index.inc()) ||
            eval(acc * nums[index], target, nums, index.inc())
    }

    private fun eval2(acc: Long, target: Long, nums: List<Long>, index: Int): Boolean {
        if (index > nums.lastIndex) return acc == target
        if (acc > target) return false
        return eval2(acc + nums[index], target, nums, index.inc()) ||
            eval2(acc * nums[index], target, nums, index.inc()) ||
            eval2("${acc}${nums[index]}".toLong(), target, nums, index.inc())
    }
}
