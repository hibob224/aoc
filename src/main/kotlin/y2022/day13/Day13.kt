package y2022.day13

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import utils.getInputFile
import utils.transform

fun main() {
    println("Part one: ${Day13.solvePartOne()}")
    println("Part two: ${Day13.solvePartTwo()}")
}

object Day13 {

    private val input = getInputFile()
        .readText()
        .split("\n\n")
        .map {
            val (left, right) = it.split("\n", limit = 2)
            Json.parseToJsonElement(left).jsonArray.transform().toMutableList() to Json.parseToJsonElement(right).jsonArray.transform().toMutableList()
        }

    fun solvePartOne(): Int = input.foldRightIndexed(0) { index, pair, acc ->
        if (compare(pair.first, pair.second) == Validity.VALID) {
            acc + index.inc()
        } else {
            acc
        }
    }

    fun solvePartTwo(): Int {
        val flatPackets = input.flatMap { listOf(it.first, it.second) }
        val index1 = flatPackets.count { compare(it, listOf<Any>(listOf(2))) == Validity.VALID } + 1
        val index2 = flatPackets.count { compare(it, listOf<Any>(listOf(6))) == Validity.VALID } + 2
        return index1 * index2
    }

    private fun compare(left: Any?, right: Any?): Validity {
        if (left == null) return Validity.VALID
        if (right == null) return Validity.NOT_VALID

        if (left is Int && right is Int) {
            return when {
                left < right -> Validity.VALID
                left == right -> Validity.UNSURE
                else -> Validity.NOT_VALID
            }
        } else if (left is List<*> && right is List<*>) {
            val l = left.takeIf { it.size >= right.size } ?: buildList(right.size) {
                addAll(left as List<Any>)
                repeat(right.size - left.size) { add(null) }
            }
            val r = right.takeIf { it.size >= left.size } ?: buildList(left.size) {
                addAll(right as List<Any>)
                repeat(left.size - right.size) { add(null) }
            }
            return l.zip(r)
                .asSequence()
                .map { compare(it.first, it.second) }
                .firstOrNull { it != Validity.UNSURE } ?: Validity.UNSURE
        } else if (left is Int && right is List<*>) {
            return compare(listOf(left), right)
        } else if (left is List<*> && right is Int) {
            return compare(left, listOf(right))
        }

        error("Failed")
    }

    enum class Validity {
        VALID, NOT_VALID, UNSURE
    }
}
