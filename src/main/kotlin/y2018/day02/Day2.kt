package y2018.day02

import utils.getInputFile

fun main() {
    println("Part one: ${Day2.solvePartOne()}")
    println("Parse two: ${Day2.solvePartTwo()}")
}

object Day2 {

    private val input = getInputFile().readLines()

    fun solvePartOne(): Int {
        var appearsTwice = 0
        var appearsThrice = 0
        input.asSequence().map { it.groupingBy { it }.eachCount() }.forEach { group ->
            if (group.containsValue(2)) {
                appearsTwice++
            }
            if (group.containsValue(3)) {
                appearsThrice++
            }
        }
        return appearsThrice * appearsTwice
    }

    fun solvePartTwo(): String {
        val ids = input.asSequence()
        ids.forEach { idOne ->
            ids.forEach { idTwo ->
                val zipped = idOne.zip(idTwo)
                if (zipped.count { it.first == it.second } == idOne.count() - 1) {
                    return zipped.map { if (it.first == it.second) it.first else "" }.joinToString(separator = "")
                }
            }
        }
        throw IllegalStateException("Fucked it")
    }
}