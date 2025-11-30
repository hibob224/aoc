package codyssi.y2025.day01

import utils.getInputFile

fun main() {
    val day = Day01()
    println("Part one: " + day.solvePartOne())
    println("Part two: " + day.solvePartTwo())
    println("Part three: " + day.solvePartThree())
}

class Day01 {

    private val input = getInputFile(example = false).readLines()
    private val nums = input.dropLast(1).map(String::toInt)
    private val operations = input.last().toCharArray().toList()

    fun solvePartOne(): Int = solve(nums, operations)

    fun solvePartTwo(): Int = solve(nums, operations.reversed())

    fun solvePartThree(): Int {
        val twoDigit = nums.chunked(2).map { (a, b) ->
            "$a$b".toInt()
        }
        return solve(twoDigit, operations.reversed())
    }

    private fun solve(nums: List<Int>, ops: List<Char>): Int {
        var out = nums.first()

        nums.drop(1).forEachIndexed { index, i ->
            val op = ops[index]
            when (op) {
                '+' -> out += i
                '-' -> out -= i
            }
        }

        return out
    }
}
