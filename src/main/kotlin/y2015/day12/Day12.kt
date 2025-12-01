package y2015.day12

import kotlinx.serialization.json.*
import template.Puzzle
import template.solve
import utils.orZero

fun main() = solve { Day12() }

class Day12 : Puzzle<Int, Int>(year = 2015, day = 12, example = false) {

    override val input = rawInput
    private val numberRegex = """-?\d+""".toRegex()

    override fun solvePartOne(): Int {
        return numberRegex
            .findAll(input)
            .map { it.value.toInt() }
            .sum()
    }

    override fun solvePartTwo(): Int {
        return Json.parseToJsonElement(input).sum()
    }

    private fun JsonElement.sum(): Int {
        return when (this) {
            is JsonObject -> if (values.any { (it as? JsonPrimitive)?.content == "red" }) {
                0
            } else {
                values.sumOf { it.sum() }
            }
            is JsonArray -> sumOf { it.sum() }
            is JsonPrimitive -> intOrNull.orZero()
        }
    }
}
