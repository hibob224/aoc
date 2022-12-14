package y2021.day18

import kotlinx.serialization.json.*
import utils.getInputFile
import utils.orZero
import utils.transform
import java.lang.Math.ceil

fun main() {
    println("Part one: ${Day18.solvePartOne()}")
    println("Part two: ${Day18.solvePartTwo()}")
}

object Day18 {

    private fun parseInput(): MutableList<MutableList<Any>> =
        getInputFile(this::class.java.packageName)
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

            // Reduce
            while (true) {
                // Attempt explode
                if (explode(work) != null) {
                    continue // Exploded an array, loop again from start
                }
                if (split(work)) {
                    continue // Split a number, loop again from start
                }
                break
            }
        }

        return work.magnitude()
    }

    fun solvePartTwo(): Int {
        val inp = parseInput()
        val magnitudes = mutableListOf<Int>()

        inp.forEach { outerR ->
            inp.forEach inner@{ innerR ->
                if (outerR == innerR) return@inner
                val inner = innerR.copy()
                val outer = outerR.copy()

                val work: MutableList<Any> = listOf(outer.toMutableList(), inner.toMutableList()).toMutableList()
                // Reduce
                while (true) {
                    // Attempt explode
                    if (explode(work) != null) {
                        continue // Exploded an array, loop again from start
                    }
                    if (split(work)) {
                        continue // Split a number, loop again from start
                    }
                    break
                }
                magnitudes += work.magnitude()
            }
        }

        return magnitudes.maxOrNull()!!
    }

    @Suppress("UNCHECKED_CAST")
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
        val l = work.first()

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
        val r = work[1]

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

    private fun List<*>.copy(): List<*> {
        return map {
            if (it is List<*>) {
                it.copy()
            } else {
                it
            }
        }
    }
}