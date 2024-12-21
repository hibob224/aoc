package y2022.day10

import utils.getInputFile

fun main() {
    println("Part one: ${Day10.solvePartOne()}")
    println("Part two:")
    Day10.solvePartTwo().forEach { row ->
        row.forEach(::print)
        println()
    }
}

object Day10 {

    private val input = getInputFile()
        .readLines()
        .map { it.split(" ") }

    fun solvePartOne(): Int {
        var cycle = 0
        val completedCommands = mutableListOf(1)
        val milestones = listOf(20, 60, 100, 140, 180, 220)
        val milestonesValues = mutableListOf<Int>()

        val performCycle = {
            cycle++
            if (cycle in milestones) {
                milestonesValues += completedCommands.sum()
            }
        }

        input.forEach {
            val command = it[0]
            performCycle() // Always do at least one cycle

            if (command == "addx") {
                performCycle() // addx has a one cycle delay before completing
                completedCommands += it[1].toInt()
            }
        }

        return milestones.zip(milestonesValues).sumOf { it.first * it.second }
    }

    fun solvePartTwo(): Array<Array<Char>> {
        val crt = Array(6) { Array(40) { '.' } }
        var spritePos = 0..2
        var cycle = 0
        var row = 0

        val drawPixel = {
            val x = cycle % crt.first().size
            if (cycle > 0 && x == 0) row++ // Move to next row when we reach the end of the current
            if (x in spritePos) {
                crt[row][x] = '#' // Sprite overlaps, change pixel to lit
            }
            cycle++
        }

        input.forEach {
            val command = it[0]
            drawPixel() // Always draw at least one pixel per command

            if (command == "addx") {
                drawPixel() // addx has a 1 cycle delay, draw an extra pixel
                val newSpriteStart = spritePos.first + it[1].toInt()
                spritePos = newSpriteStart..newSpriteStart + 2
            }
        }

        return crt
    }
}
