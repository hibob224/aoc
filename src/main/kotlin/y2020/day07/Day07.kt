package y2020.day07

import utils.orZero
import java.io.File

fun main() {
    println("Part one: ${Day07.solvePartOne()}")
    println("Part two: ${Day07.solvePartTwo()}")
}

object Day07 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val colourRegex = """^(.*) bags? contain.*${'$'}""".toRegex()
    private val contentsRegex = """(\d) (.*?) bag""".toRegex()

    private val bags: List<Bag> =
        File("src/main/kotlin/$directory/input.txt")
            .readLines()
            .map { line ->
                val (bagColour) = colourRegex.matchEntire(line)!!.destructured
                val contents = contentsRegex.findAll(line)
                    .iterator()
                    .asSequence()
                    .map {
                        val (quantity, colour) = it.destructured
                        colour to quantity.toInt()
                    }
                    .toMap()
                Bag(bagColour, contents)
            }

    fun solvePartOne(): String = bagContent("shiny gold").size.toString()

    fun solvePartTwo(): String = bagContents("shiny gold").toString()

    private fun bagContent(target: String): Set<String> =
        bags.filter { target in it.contents.keys }
            .map { setOf(it.colour) + bagContent(it.colour) }
            .flatten()
            .toSet()

    private fun bagContents(target: String): Int =
        bags.find { it.colour == target }?.contents?.entries?.sumBy {
            it.value + (it.value * bagContents(it.key))
        }.orZero()

    data class Bag(
        val colour: String,
        val contents: Map<String, Int>
    )
}