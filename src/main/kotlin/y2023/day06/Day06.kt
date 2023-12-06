package y2023.day06

import utils.getInputFile

fun main() {
    println("Part one: ${Day06.solvePartOne()}")
    println("Part two: ${Day06.solvePartTwo()}")
}

object Day06 {

    private val input =
        getInputFile(this::class.java.packageName, example = true)
            .readLines()
            .joinToString(separator = "")
            .let {
                val regex = """(\d+)""".toRegex()
                val matches = regex.findAll(it).toList().map { it.value.toInt() }
                val raceTimes = matches.subList(0, matches.size / 2)
                val raceDistances = matches.subList(matches.size / 2, matches.size)
                raceTimes.zip(raceDistances, ::Race)
            }

    fun solvePartOne(): Int {
        return input
            .map { race ->
                (0..race.time)
                    .map { speed ->
                        val timeLeft = race.time - speed
                        timeLeft * speed
                    }
                    .count { it > race.maxDistance }
            }.reduce { acc, i -> acc * i }
    }

    fun solvePartTwo(): Long = 0

    data class Race(
        val time: Int,
        val maxDistance: Int,
    )
}
