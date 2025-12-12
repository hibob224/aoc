package y2025.day10

import com.microsoft.z3.Context
import com.microsoft.z3.Status
import template.Puzzle
import template.solve

fun main() = solve { Day10() }

class Day10 : Puzzle<Int, Int>(2025, 10, example = false) {

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

    override fun solvePartTwo(): Int {
        // A lot of help on this one, would've never known about Z3
        return input.sumOf { puzzle ->
            Context().use { ctx ->
                val opt = ctx.mkOptimize()
                val vars = puzzle.buttons.indices.map { ctx.mkIntConst("b$it") }
                vars.forEach { opt.Add(ctx.mkGe(it, ctx.mkInt(0))) }
                puzzle.joltages.indices.forEach { i ->
                    val terms = puzzle.buttons.withIndex().filter { i in it.value }.map { vars[it.index] }
                    if (terms.isNotEmpty()) {
                        val sum = if (terms.size == 1) {
                            terms[0]
                        } else {
                            ctx.mkAdd(*terms.toTypedArray())
                        }
                        opt.Add(ctx.mkEq(sum, ctx.mkInt(puzzle.joltages[i])))
                    } else if (puzzle.joltages[i] != 0) {
                        error("Failed")
                    }
                }
                opt.MkMinimize(ctx.mkAdd(*vars.toTypedArray()))
                if (opt.Check() == Status.SATISFIABLE) {
                    vars.sumOf { opt.model.evaluate(it, false).toString().toInt() }
                } else {
                    0
                }
            }
        }
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
