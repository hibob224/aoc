package y2019.day06

import utils.getInputFile

fun main() {
    println("Part one: ${Day6.solvePartOne()}")
    println("Part two: ${Day6.solvePartTwo()}")
}

object Day6 {

    private fun parseInput(): Map<String, List<String>> =
        getInputFile()
            .readLines()
            .map { it.split(")")[0] to it.split(")")[1] }
            .groupBy({ it.first }) { it.second }

    fun solvePartOne(): String {
        val input = parseInput()
        var count = 0

        input.forEach {
            count += it.value.size
            val multiplier = it.value.size
            var target = it.key
            while (true) {
                input.entries.find { it.value.contains(target) }?.let {
                    count += 1 * multiplier
                    target = it.key
                } ?: break
            }
        }

        return count.toString()
    }

    fun solvePartTwo(): String {
        val input = parseInput()
        val youLoc = input.entries.find { it.value.contains("YOU") }!!.key
        val santaLoc = input.entries.find { it.value.contains("SAN") }!!.key

        val steps = mutableListOf(listOf(youLoc))

        while (true) {
            var newFrontline = mutableListOf<String>()

            newFrontline.addAll(steps.last().flatMap { input[it].orEmpty() }) // Add all objects that orbit our current objects
            newFrontline.addAll(steps.last().mapNotNull { loc -> // Add all our direct orbits
                input.entries.find { it.value.contains(loc) }?.key
            })
            newFrontline = newFrontline.filterNot { steps.flatten().contains(it) }.toMutableList() // Make sure we're not checking locations we've checked before
            newFrontline = newFrontline.distinct().toMutableList() // Remove duplicates
            steps.add(newFrontline)
            if (newFrontline.contains(santaLoc)) {
                // We've found Santa!
                break
            }
        }

        // Decrease steps by 1 because we don't count the starting location
        return steps.size.dec().toString()
    }
}
