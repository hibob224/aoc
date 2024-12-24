package y2015.day10

fun main() {
    val day = Day10()
    println("Part one: " + day.solvePartOne())
    println("Part two: " + day.solvePartTwo())
}

class Day10 {

    private val regex = """(\d)\1*""".toRegex()
    private val input = "1113122113"

    fun solvePartOne(): Int {
        return generateSequence(input, ::lookAndSee).elementAt(40).length
    }

    fun solvePartTwo(): Int {
        return generateSequence(input, ::lookAndSee).elementAt(50).length
    }

    private fun lookAndSee(inp: String): String {
        val matches = regex.findAll(inp)
        return matches.joinToString("") { "${it.groups.first()!!.value.length}${it.groups[1]!!.value}" }
    }
}
