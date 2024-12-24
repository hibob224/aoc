package y2024.day24

import utils.getInputFile

fun main() {
    val day = Day24()
    println("Part one: " + day.solvePartOne())
    println("Part two: " + day.solvePartTwo())
}

class Day24 {

    private val initialWireValues: Map<String, Boolean>
    private val connections: Map<String, Triple<String, String, Operation>>
    private val connectionRegex = """^(.{3}) (AND|OR|XOR) (.{3}) -> (.{3})$""".toRegex()

    init {
        val (wireInput, connectionInput) = getInputFile(example = false).readText().split("\n\n")

        initialWireValues = wireInput
            .lines()
            .associate {
                val (wire, value) = it.split(": ")
                wire to (value == "1")
            }
        connections = connectionInput
            .lines()
            .associate {
                val (a, op, b, c) = connectionRegex.find(it)!!.destructured
                c to Triple(a, b, Operation.valueOf(op))
            }
    }

    fun solvePartOne(): Long { // 1011110000111111111110101101011110010001110000 too high
        val wireValues = initialWireValues.toMutableMap()

        return connections.keys
            .filter { it.startsWith("z") }
            .sortedWith(naturalOrderComparator)
            .reversed()
            .joinToString("") { if (wireValues.wireValue(it)) "1" else "0" }
            .toLong(2)
//            .map { wireValues.wireValue(it) }
    }

    fun solvePartTwo(): Long {
        return 0
    }

    private enum class Operation {
        AND, OR, XOR
    }

    private fun MutableMap<String, Boolean>.wireValue(k: String): Boolean {
        return get(k) ?: run {
            val connection = connections.getValue(k)
            val v = when (connection.third) {
                Operation.AND -> wireValue(connection.first) && wireValue(connection.second)
                Operation.OR -> wireValue(connection.first) || wireValue(connection.second)
                Operation.XOR -> wireValue(connection.first) xor wireValue(connection.second)
            }
            put(k, v)
            v
        }
    }

    val naturalOrderComparator = Comparator<String> { str1, str2 ->
        val regex = "\\d+".toRegex()
        var i = 0
        while (i < str1.length && i < str2.length) {
            val char1 = str1[i]
            val char2 = str2[i]
            if (char1.isDigit() && char2.isDigit()) {
                val num1 = regex.find(str1, i)?.value?.toInt() ?: 0
                val num2 = regex.find(str2, i)?.value?.toInt() ?: 0
                return@Comparator num1.compareTo(num2)
            } else if (char1 != char2) {
                return@Comparator char1.compareTo(char2)
            }
            i++
        }
        str1.compareTo(str2)
    }
}
