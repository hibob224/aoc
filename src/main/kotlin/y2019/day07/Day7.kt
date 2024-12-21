package y2019.day07

import IntCodeComputer
import utils.getInputFile
import java.util.concurrent.Executors
import kotlin.math.max

fun main() {
    println("Part one: ${Day7.solvePartOne()}")
    println("Part two: ${Day7.solvePartTwo()}")
}

object Day7 {

    private fun parseInput(): List<Long> =
        getInputFile()
            .readLines()
            .first()
            .split(",")
            .map { it.toLong() }

    fun solvePartOne(): String {
        var highestSignal = 0L
        val list = parseInput()

        permutations(0..4L).forEach { perm ->
            val ampA = IntCodeComputer(list.toLongArray()).also {
                it.addInput(perm.a, 0)
            }.call()
            val ampB = IntCodeComputer(list.toLongArray()).also {
                it.addInput(perm.b, ampA)
            }.call()
            val ampC = IntCodeComputer(list.toLongArray()).also {
                it.addInput(perm.c, ampB)
            }.call()
            val ampD = IntCodeComputer(list.toLongArray()).also {
                it.addInput(perm.d, ampC)
            }.call()
            highestSignal = max(IntCodeComputer(list.toLongArray()).also {
                it.addInput(perm.e, ampD)
            }.call(), highestSignal)
        }

        return highestSignal.toString()
    }

    fun solvePartTwo(): String {
        val list = parseInput()
        var highestSignal = 0L

        permutations(5L..9).forEach { perm ->
            val ampE = IntCodeComputer(list.toLongArray()).also {
                it.addInput(perm.e)
            }
            val ampD = IntCodeComputer(list.toLongArray()).also {
                it.addInput(perm.d)
                it.outputComputer = ampE
            }
            val ampC = IntCodeComputer(list.toLongArray()).also {
                it.addInput(perm.c)
                it.outputComputer = ampD
            }
            val ampB = IntCodeComputer(list.toLongArray()).also {
                it.addInput(perm.b)
                it.outputComputer = ampC
            }
            val ampA = IntCodeComputer(list.toLongArray()).also {
                it.addInput(perm.a, 0) // Amp A gets an extra starting input of 0 as per instructions
                it.outputComputer = ampB
            }
            ampE.outputComputer = ampA

            Executors.newCachedThreadPool().also {
                it.submit(ampA)
                it.submit(ampB)
                it.submit(ampC)
                it.submit(ampD)
                highestSignal = max(highestSignal, it.submit(ampE).get())
            }
        }

        return highestSignal.toString()
    }

    private fun permutations(range: LongRange): List<Permutation> {
        val permutations = mutableListOf<Permutation>()
        for (a in range) {
            for (b in range) {
                for (c in range) {
                    for (d in range) {
                        for (e in range) {
                            if (setOf(a, b, c, d, e).size != 5) {
                                // Don't want any duplicate inputs
                                continue
                            }
                            permutations.add(Permutation(a, b, c, d, e))
                        }
                    }
                }
            }
        }
        return permutations
    }

    data class Permutation(
        val a: Long,
        val b: Long,
        val c: Long,
        val d: Long,
        val e: Long
    )
}
