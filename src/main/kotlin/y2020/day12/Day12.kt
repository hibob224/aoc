package y2020.day12

import utils.Point
import utils.getInputFile

fun main() {
    println("Part one: ${Day12.solvePartOne()}")
    println("Part two: ${Day12.solvePartTwo()}")
}

object Day12 {

    private val regex = """^([A-Z])(\d+)$""".toRegex()
    private val input: List<Instruction> =
        getInputFile(this::class.java.packageName)
            .readLines()
            .map { line ->
                regex.matchEntire(line)?.let {
                    when (it.groupValues[1].first()) {
                        'N' -> Instruction.North(it.groupValues[2].toInt())
                        'S' -> Instruction.South(it.groupValues[2].toInt())
                        'W' -> Instruction.West(it.groupValues[2].toInt())
                        'E' -> Instruction.East(it.groupValues[2].toInt())
                        'R' -> Instruction.Right(it.groupValues[2].toInt())
                        'L' -> Instruction.Left(it.groupValues[2].toInt())
                        'F' -> Instruction.Forward(it.groupValues[2].toInt())
                        else -> throw IllegalArgumentException("Illegal move")
                    }
                }
            }
            .requireNoNulls()

    fun solvePartOne(): String {
        val boat = Boat()
        input.forEach {
            boat.move(it)
        }
        return boat.location.manhattan(Point(0, 0)).toString()
    }

    fun solvePartTwo(): String {
        return ""
    }

    class Boat {
        var location: Point = Point(0, 0)
        var direction = Direction.E

        fun move(instruction: Instruction) {
            val i = when (instruction) {
                is Instruction.Forward -> when (direction) {
                    Direction.N -> Instruction.North(instruction.value)
                    Direction.S -> Instruction.South(instruction.value)
                    Direction.E -> Instruction.East(instruction.value)
                    Direction.W -> Instruction.West(instruction.value)
                }

                is Instruction.Left -> {
                    direction = direction.rotate(-instruction.value)
                    return
                }

                is Instruction.Right -> {
                    direction = direction.rotate(instruction.value)
                    return
                }

                else -> instruction
            }
            location = when (i) {
                is Instruction.North -> location.copy(y = location.y - i.value)
                is Instruction.South -> location.copy(y = location.y + i.value)
                is Instruction.East -> location.copy(x = location.x + i.value)
                is Instruction.West -> location.copy(x = location.x - i.value)
                else -> throw IllegalStateException("Shoulda been fixed by now")
            }
        }
    }

    enum class Direction(private val angle: Int) {
        N(0), E(90), S(180), W(270);

        fun rotate(rotate: Int): Direction {
            var rotated = (angle + rotate)
            if (rotated >= 360) {
                rotated -= 360
            } else if (rotated < 0) {
                rotated += 360
            }
            return values().find { it.angle == rotated }!!
        }
    }

    sealed class Instruction(val value: Int) {
        class North(value: Int) : Instruction(value)
        class South(value: Int) : Instruction(value)
        class West(value: Int) : Instruction(value)
        class East(value: Int) : Instruction(value)
        class Left(value: Int) : Instruction(value)
        class Right(value: Int) : Instruction(value)
        class Forward(value: Int) : Instruction(value)
    }
}