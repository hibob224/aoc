package y2021.day18

import kotlinx.serialization.json.*
import utils.orZero
import java.io.File
import java.lang.Math.ceil

fun main() {
    println("Part one: ${Day18.solvePartOne()}")
    println("Part two: ${Day18.solvePartTwo()}")
}

object Day18 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private fun parseInput(): MutableList<MutableList<Any>> =
        File("src/main/kotlin/$directory/input.txt")
            .readLines()
            .map {
                Json.parseToJsonElement(it).jsonArray.transform().toMutableList()
            }
            .toMutableList()

    fun solvePartOne(): Int {
        val input = parseInput()
        var work = input.first()

        (1..input.lastIndex).forEach { index ->
            // Add
            work = listOf(work, input[index]).toMutableList()
//            println(work)

            // Reduce
            while (true) {
//                println("Reduce")
                // Attempt explode
                if (explode(work) != null) {
//                    println("Explode - $work")
                    continue // Exploded an array, loop again from start
                }
                if (split(work)) {
//                    println("Split - $work")
                    continue // Split a number, loop again from start
                }
                break
            }
        }

//        println(work)



        return work.magnitude()
    }

    fun solvePartTwo(): Int {
        return -1
    }

    private fun explode(work: MutableList<Any>, depth: Int = 0): Pair<Int?, Int?>? {
        val (left, right) = work

        if (depth < 3 || (left is Int && right is Int)) {
            if (left is List<*>) {
                val result = explode(left as MutableList<Any>, depth.inc())
                if (result != null) {
                    if (right is Int) {
                        work[1] = right + result.second.orZero()
                    } else {
                        addRight(right as MutableList<Any>, result.second.orZero())
                    }
                    return result.first to null
                }
            }
            if (right is List<*>) {
                val result = explode(right as MutableList<Any>, depth.inc())
                if (result != null) {
                    if (left is Int) {
                        work[0] = left + result.first.orZero()
                    } else {
                        addLeft(left as MutableList<Any>, result.first.orZero())
                    }
                    return null to result.second
                }
            }
            return null
        }

        return if (left is List<*>) {
            val (l, r) = left.map { it as Int }
            work[0] = 0
            if (right is Int) {
                work[1] = right + r
            } else {
                addRight(right as MutableList<Any>, r)
            }
            l to null
        } else if (right is List<*>) {
            val (l, r) = right.map { it as Int }
            work[1] = 0
            if (left is Int) {
                work[0] = left + l
            } else {
                addLeft(left as MutableList<Any>, l)
            }
            null to r
        } else {
            null
        }
    }

    private fun addRight(work: MutableList<Any>, add: Int): Boolean {
        if (add == 0) {
            return false
        }
        val (l, r) = work

        if (l is Int) {
            work[0] = l + add
            return true
        }
        if (l is List<*>) {
            return addRight(l as MutableList<Any>, add)
        }
        return false
    }

    private fun addLeft(work: MutableList<Any>, add: Int): Boolean {
        if (add == 0) {
            return false
        }
        val (l, r) = work

        if (r is Int) {
            work[1] = r + add
            return true
        }
        if (r is List<*>) {
            return addLeft(r as MutableList<Any>, add)
        }
        return false
    }

    private fun split(work: MutableList<Any>): Boolean {
        val (l, r) = work

        if (l is Int) {
            if (l >= 10) {
                // Split
                val newLeft = l.div(2)
                val newRight = ceil(l.div(2.0)).toInt()
                work[0] = mutableListOf(newLeft, newRight)
                return true
            }
        } else if (split(l as MutableList<Any>)) {
            return true
        }

        if (r is Int) {
            if (r >= 10) {
                // Split
                val newLeft = r.div(2)
                val newRight = ceil(r.div(2.0)).toInt()
                work[1] = mutableListOf(newLeft, newRight)
                return true
            }
        } else {
            return split(r as MutableList<Any>)
        }
        return false
    }

    private fun JsonArray.transform(): List<Any> = map {
        if (it is JsonArray) {
            it.transform()
        } else {
            it.jsonPrimitive.int
        }
    }

    private fun List<*>.magnitude(): Int {
        val (left, right) = this
        val firstVal = 3 * if (left is List<*>) {
            left.magnitude()
        } else {
            left as Int
        }
        val secondVal = 2 * if (right is List<*>) {
            right.magnitude()
        } else {
            right as Int
        }
        return firstVal + secondVal
    }
}