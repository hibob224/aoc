package y2019.day12

import utils.getInputFile
import kotlin.math.abs

fun main() {
    println("Part one: ${Day12.solvePartOne()}")
    println("Part two: ${Day12.solvePartTwo()}")
}

object Day12 {

    private val parseRegex = "<x=(-?\\d+), y=(-?\\d+), z=(-?\\d+)>".toRegex()

    private fun parseInput(): List<Moon> =
        getInputFile().readLines()
            .map {
                val (x, y, z) = parseRegex.find(it)!!.destructured
                Moon(x.toInt(), y.toInt(), z.toInt())
            }

    fun solvePartOne(): String {
        val moons = parseInput()
        val pairs = listOf(
            moons[0] to moons[1],
            moons[0] to moons[2],
            moons[0] to moons[3],
            moons[1] to moons[2],
            moons[1] to moons[3],
            moons[2] to moons[3]
        )

        repeat(1000) {
            pairs.forEach {
                it.first.updateVelocity(it.second)
            }
            moons.forEach { it.applyVelocity() }
        }

        return moons.sumOf { it.energy() }.toString()
    }

    fun solvePartTwo(): String {
        val input = parseInput()
        val moons = input.toList()
        val pairs = listOf(
            moons[0] to moons[1],
            moons[0] to moons[2],
            moons[0] to moons[3],
            moons[1] to moons[2],
            moons[1] to moons[3],
            moons[2] to moons[3]
        )
        var xIndex = 0L
        var yIndex = 0L
        var zIndex = 0L
        var loop = 0L

        while (xIndex == 0L || yIndex == 0L || zIndex == 0L) {
            loop += 1
            pairs.forEach { it.first.updateVelocity(it.second) }
            moons.forEach { it.applyVelocity() }
            if (xIndex == 0L && moons.all { it.xVelocity == 0 } && input.map { it.x } == moons.map { it.x }) {
                xIndex = loop
            }
            if (yIndex == 0L && moons.all { it.yVelocity == 0 } && input.map { it.y } == moons.map { it.y }) {
                yIndex = loop
            }
            if (zIndex == 0L && moons.all { it.zVelocity == 0 } && input.map { it.z } == moons.map { it.z }) {
                zIndex = loop
            }
        }

        return lcm(xIndex, lcm(yIndex, zIndex)).times(2).toString()
    }

    private fun gcd(a: Long, b: Long): Long = if (b != 0L) gcd(b, a % b) else a
    private fun lcm(a: Long, b: Long): Long = a * b / gcd(a, b)
}

data class Moon(
    var x: Int,
    var y: Int,
    var z: Int,
    var xVelocity: Int = 0,
    var yVelocity: Int = 0,
    var zVelocity: Int = 0
) {

    fun updateVelocity(other: Moon) {
        if (other.x < x) {
            other.xVelocity += 1
            xVelocity -= 1
        } else if (other.x > x) {
            other.xVelocity -= 1
            xVelocity += 1
        }
        if (other.y < y) {
            other.yVelocity += 1
            yVelocity -= 1
        } else if (other.y > y) {
            other.yVelocity -= 1
            yVelocity += 1
        }
        if (other.z < z) {
            other.zVelocity += 1
            zVelocity -= 1
        } else if (other.z > z) {
            other.zVelocity -= 1
            zVelocity += 1
        }
    }

    fun applyVelocity() {
        x += xVelocity
        y += yVelocity
        z += zVelocity
    }

    fun energy(): Int = (abs(x) + abs(y) + abs(z)) * (abs(xVelocity) + abs(yVelocity) + abs(zVelocity))

    override fun toString(): String = "pos=<x=$x, y=$y, z=$z>, vel=<x=$xVelocity, y=$yVelocity, z=$zVelocity>"
}