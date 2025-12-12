package y2025.day10

import template.Puzzle
import template.solve

fun main() = solve { Day10() }

class Day10 : Puzzle<Int, Long>(2025, 10, example = false) {

    override val input = rawInput
        .lines()
        .map {
            val split = it.split(" ")
            val target = split.first().drop(1).dropLast(1)
            val joltages = split.last().drop(1).dropLast(1).split(",").map(String::toInt)
            val buttons = split.drop(1).dropLast(1).map {
                it.drop(1).dropLast(1).split(",").map(String::toInt)
            }
            Puzzle(
                target = target,
                buttons = buttons,
                joltages = joltages,
            )
        }

    override fun solvePartOne(): Int {
        var out = 0

        input.forEach { puzzle ->
            val queue = ArrayDeque<State>()
            queue.add(State(".".repeat(puzzle.target.length), 0))
            val visited = mutableSetOf<Pair<String, List<Int>>>()

            while (queue.isNotEmpty()) {
                val (lights, steps) = queue.removeFirst()
                if (lights == puzzle.target) {
                    out += steps
                    break // Found target, sum steps and break out to the next puzzle
                }

                for (button in puzzle.buttons) {
                    if (!visited.add(lights to button)) continue // Been here before, step searching this path
                    val newLights = lights.toMutableList()
                    for (change in button) {
                        newLights[change] = if (newLights[change] == '.') '#' else '.'
                    }
                    queue.add(State(newLights.joinToString(separator = ""), steps + 1))
                }
            }
        }

        return out
    }

    override fun solvePartTwo(): Long {
        return 0
    }

    data class State(
        val lights: String,
        val steps: Int,
    )

    data class Puzzle(
        val target: String,
        val buttons: List<List<Int>>,
        val joltages: List<Int>,
    )
}
