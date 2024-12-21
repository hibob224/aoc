package y2015.day07

import utils.getInputFile

fun main() {
    println("Part one: " + Day07.solvePartOne())
    println("Part two: " + Day07.solvePartTwo())
}

object Day07 {

    private val input: Map<String, Operation> = getInputFile()
        .readLines()
        .map { it.split(" -> ") }
        .associate { (ins, out) ->
            out to when {
                "AND" in ins -> {
                    val (l, r) = ins.split(" AND ")
                    Operation.And(l, r, out)
                }
                "OR" in ins -> {
                    val (l, r) = ins.split(" OR ")
                    Operation.Or(l, r, out)
                }
                "LSHIFT" in ins -> {
                    val (l, r) = ins.split(" LSHIFT ")
                    Operation.LShift(l, r.toInt(), out)
                }
                "RSHIFT" in ins -> {
                    val (l, r) = ins.split(" RSHIFT ")
                    Operation.RShift(l, r.toInt(), out)
                }
                "NOT" in ins -> {
                    val x = ins.removePrefix("NOT ")
                    Operation.Not(x, out)
                }
                else -> Operation.Set(ins, out)
            }
        }
    private val wires = mutableMapOf<String, Int>()

    fun solvePartOne(): Int = get("a")

    fun solvePartTwo(): Int {
        val p1 = solvePartOne()
        wires.clear()
        wires["b"] = p1
        return get("a")
    }

    private fun get(k: String): Int {
        return k.toIntOrNull() ?: wires[k] ?: run {
            val v = when (val ins = input[k]!!) {
                is Operation.And -> get(ins.a) and get(ins.b)
                is Operation.Or -> get(ins.a) or get(ins.b)
                is Operation.LShift -> get(ins.a) shl ins.b
                is Operation.RShift -> get(ins.a) shr ins.b
                is Operation.Not -> 65536 + get(ins.a).inv()
                is Operation.Set -> get(ins.v)
            }
            wires[k] = v
            v
        }
    }

    sealed interface Operation {
        data class And(
            val a: String,
            val b: String,
            val out: String,
        ) : Operation

        data class Or(
            val a: String,
            val b: String,
            val out: String,
        ) : Operation

        data class LShift(
            val a: String,
            val b: Int,
            val out: String,
        ) : Operation

        data class RShift(
            val a: String,
            val b: Int,
            val out: String,
        ) : Operation

        data class Not(
            val a: String,
            val out: String,
        ) : Operation

        data class Set(
            val v: String,
            val out: String,
        ) : Operation
    }
}
