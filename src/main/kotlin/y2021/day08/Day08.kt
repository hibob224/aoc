package y2021.day08

import utils.getInputFile

fun main() {
    println("Part one: ${Day08.solvePartOne()}")
    println("Part two: ${Day08.solvePartTwo()}")
}

object Day08 {

    private val regex =
        """^([a-z]{2,7}) ([a-z]{2,7}) ([a-z]{2,7}) ([a-z]{2,7}) ([a-z]{2,7}) ([a-z]{2,7}) ([a-z]{2,7}) ([a-z]{2,7}) ([a-z]{2,7}) ([a-z]{2,7}) \| ([a-z]{2,7}) ([a-z]{2,7}) ([a-z]{2,7}) ([a-z]{2,7})${'$'}""".toRegex()

    private val displays: List<Display> =
        getInputFile(this::class.java.packageName)
            .readLines()
            .map {
                val match = regex.find(it)!!.groupValues
                val input = match.subList(1, 11).map(String::toCharArray)
                val output = match.subList(11, 15).map(String::toCharArray)
                Display(input, output)
            }

    fun solvePartOne(): Int {
        val desiredLengths = listOf(2, 4, 3, 7)
        return displays
            .flatMap(Display::outputs)
            .count { it.size in desiredLengths }
    }

    fun solvePartTwo(): Int {
        return displays.sumOf(Display::calculateValue)
    }
}

data class Display(val inputs: List<CharArray>, val outputs: List<CharArray>) {

    fun calculateValue(): Int {
        val one = inputs.first { it.size == 2 }.toList().toSet()
        val four = inputs.first { it.size == 4 }.toList().toSet()
        val seven = inputs.first { it.size == 3 }.toList().toSet()
        val eight = inputs.first { it.size == 7 }.toList().toSet()
        val nine = inputs.first { it.size == 6 && it.intersect(four.toList().toSet()).size == 4 }.toList().toSet()

        val e = (eight - nine).first()

        val six = inputs.first {
            it.size == 6 &&
                    it.intersect(seven).size == 2
        }.toList().toSet()
        val zero = inputs.first {
            it.size == 6 &&
                    it.intersect(four).size == 3 &&
                    it.intersect(seven).size == 3
        }.toList().toSet()
        val two = inputs.first {
            it.size == 5 &&
                    e in it &&
                    it.intersect(six).size == 4
        }.toList().toSet()
        val three = inputs.first {
            it.size == 5 &&
                    e !in it &&
                    it.intersect(six).size == 4
        }.toList().toSet()
        val five = inputs.first {
            it.size == 5 &&
                    it.intersect(six).size == 5
        }.toList().toSet()

        val mappedOutputs = listOf(zero, one, two, three, four, five, six, seven, eight, nine)

        return outputs.map { output ->
            mappedOutputs.indexOfFirst {
                output.size == it.size && output.intersect(it).size == output.size
            }
        }.joinToString(separator = "") { "$it" }.toInt()
    }
}