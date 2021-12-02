package y2021.day02

import java.io.File

fun main() {
    println("Part one: ${Day02.solvePartOne()}")
    println("Part two: ${Day02.solvePartTwo()}")
}

object Day02 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val REGEX = """^([a-z]*) (\d*)$""".toRegex()
    private val input: List<SubMove> =
        File("src/main/kotlin/$directory/input.txt")
            .readLines()
            .map(::SubMove)

    fun solvePartOne(): Int {
        val final = input.fold(0 to 0) { current, move ->
            move.makeMove(current)
        }
        return final.first * final.second
    }

    fun solvePartTwo(): Int {
        val final = input.fold(Triple(0, 0, 0)) { current, move ->
            move.makeMove2(current)
        }
        return final.first * final.second
    }
}

class SubMove(input: String) {

    private val command: Command
    private val distance: Int

    init {
        val matches = moveRegex.find(input)!!.groupValues
        command = Command.valueOf(matches[1])
        distance = matches[2].toInt()
    }

    companion object {
        private val moveRegex = """^([a-z]*) (\d*)$""".toRegex()
    }

    fun makeMove(current: Pair<Int, Int>): Pair<Int, Int> = when (command) {
        Command.forward -> current.copy(first = current.first + distance)
        Command.up -> current.copy(second = current.second - distance)
        Command.down -> current.copy(second = current.second + distance)
    }

    fun makeMove2(current: Triple<Int, Int, Int>): Triple<Int, Int, Int> = when (command) {
        Command.forward -> current.copy(first = current.first + distance, second = current.second + (distance * current.third))
        Command.up -> current.copy(third = current.third - distance)
        Command.down -> current.copy(third = current.third + distance)
    }
}

enum class Command {
    forward, up, down
}