package y2024.day11

import utils.getInputFile
import utils.isEven

fun main() {
    println("Part one: " + Day11.solvePartOne())
    println("Part two: " + Day11.solvePartTwo())
}

object Day11 {

    private val input = getInputFile(this::class.java.packageName, example = false)
        .readText()
        .split(' ')

    fun solvePartOne(): Int {
        var blinks = input
        repeat(25) {
            blinks = blink(blinks)
        }
        return blinks.size
    }

    fun solvePartTwo(): Long {
        return 0
    }

    private fun blink(input: List<String>) = buildList {
        input.forEach { stone ->
//                println("Handling $stone")
            when {
                stone == "0" -> {
//                        println("0 -> 1")
                    add("1")
                }
                stone.length.isEven -> {
                    val split = listOf(
                        stone.substring(0, stone.length / 2),
                        stone.substring(stone.length / 2).toLong().toString(),
                    )
//                        println(split)
                    addAll(split)
                }
                else -> add(stone.toLong().times(2024).toString())
            }
        }
    }
}
