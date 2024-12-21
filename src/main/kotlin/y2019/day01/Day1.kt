package y2019.day01

import utils.getInputFile
import utils.orZero

fun main() {
    println("Part one: ${Day1.solvePartOne()}")
    println("Part two: ${Day1.solvePartTwo()}")
}

object Day1 {

    private val moduleMasses = getInputFile().readLines().map(String::toInt)

    fun solvePartOne(): String = moduleMasses.sumBy { calculateRequiredFuel(it) }.toString()

    fun solvePartTwo(): String {
        return moduleMasses.fold(0) { acc, mass ->
            var requiredFuel = 0
            var temp = mass
            while (temp > 0) {
                temp = calculateRequiredFuel(temp)
                requiredFuel += temp
            }
            acc + requiredFuel
        }.toString()
    }

    private fun calculateRequiredFuel(mass: Int) = (mass / 3 - 2).takeIf { it > 0 }.orZero()
}
