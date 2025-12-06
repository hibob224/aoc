package y2025.day06

import template.Puzzle
import template.solve
import utils.product
import utils.zip

fun main() = solve { Day06() }

class Day06 : Puzzle<Long, Long>(2025, 6, example = false) {

    override val input = rawInput.lines()

    override fun solvePartOne(): Long {
        val t = input.map {
            it
                .split(" ")
                .filter(String::isNotEmpty)
        }
        return zip(*t.toTypedArray())
            .sumOf {
                val nums = it.dropLast(1).map(String::toLong)
                when (val op = it.last()) {
                    "*" -> nums.product()
                    "+" -> nums.sum()
                    else -> error("Unknown op: $op")
                }
            }
    }

    override fun solvePartTwo(): Long {
        val largestWidth = input.maxOf(String::lastIndex)
        val sums = mutableListOf<Long>()
        val nums = mutableListOf<Long>()

        col@ for (x in largestWidth downTo 0) {
            var str = ""
            for (y in 0..input.lastIndex) {
                when (val char = input[y].getOrNull(x)) {
                    '+' -> {
                        if (str.isNotEmpty()) nums += str.toLong()
                        sums += nums.sum()
                        nums.clear()
                        continue@col
                    }
                    '*' -> {
                        if (str.isNotEmpty()) nums += str.toLong()
                        sums += nums.product()
                        nums.clear()
                        continue@col
                    }
                    null, ' ' -> {}
                    else -> str += char
                }
            }
            if (str.isNotEmpty()) nums += str.toLong()
        }
        return sums.sum()
    }
}
