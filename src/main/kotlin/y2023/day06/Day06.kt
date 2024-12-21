package y2023.day06

import utils.getInputFile

fun main() {
    println("Part one: ${Day06.solvePartOne()}")
    println("Part two: ${Day06.solvePartTwo()}")
}

object Day06 {

    private val input =
        getInputFile()
            .readLines()
            .joinToString(separator = "")
            .let {
                val regex = """(\d+)""".toRegex()
                val matches = regex.findAll(it).toList().map { it.value.toLong() }
                val raceTimes = matches.subList(0, matches.size / 2)
                val raceDistances = matches.subList(matches.size / 2, matches.size)
                raceTimes.zip(raceDistances, ::Race)
            }

    fun solvePartOne(): Int {
        return input
            .map { race ->
                solve(race.time, race.maxDistance)
            }.reduce { acc, i -> acc * i }
    }

    fun solvePartTwo(): Int {
        val time = input.joinToString(separator = "") { it.time.toString() }.toLong()
        val maxDistance = input.joinToString(separator = "") { it.maxDistance.toString() }.toLong()
        return solve(time, maxDistance)
    }

    private fun solve(time: Long, distance: Long): Int =
        (0..time)
            .count { speed ->
                val timeLeft = time - speed
                timeLeft * speed > distance
            }

    data class Race(
        val time: Long,
        val maxDistance: Long,
    )
}
