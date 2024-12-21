package y2017.day02

import utils.getInputFile

fun main() {
    println("Part one: " + Day02.solvePartOne())
    println("Part two: " + Day02.solvePartTwo())
}

object Day02 {

    private val input = getInputFile().readLines()

    fun solvePartOne(): Int {
        var sum = 0
        input.map { line ->
            line.split("\t")
        }.forEach {
            it.map { it.toInt() }.sorted().let { // Map each value to an int, sort the list
                sum += (it.last() - it.first())
            }
        }
        return sum
    }

    fun solvePartTwo(): Int {
        var sum = 0
        input
            .map { line -> line.split("\t") }
            .forEach {
                it.map { it.toInt() }.let { // Map each value to an int
                    it.combinations().forEach {
                        if ((it.first % it.second) == 0) {
                            sum += it.first / it.second
                        }
                    }
                }
            }
        return sum
    }

    private fun <T> List<T>.combinations(): ArrayList<Pair<T, T>> {
        val list = arrayListOf<Pair<T, T>>()
        forEachIndexed { indexOuter, valueOuter ->
            forEachIndexed { indexInner, valueInner ->
                if (indexOuter != indexInner) {
                    list.add(Pair(valueOuter, valueInner))
                }
            }
        }
        return list
    }
}
