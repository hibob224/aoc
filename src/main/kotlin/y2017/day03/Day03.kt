package y2017.day03

fun main() {
    println("Part one: " + Day03.solvePartOne())
    println("Part two: " + Day03.solvePartTwo())
}

object Day03 {

    fun solvePartOne(): Int {
        val grid: HashMap<Int, String> = hashMapOf()
        var x = 0
        var y = 0
        var dist = 1
        var index = 2
        var direction = 'e'

        for (c in 1..1024) { // Create a huge spiral
            for (i in 1..2) { // Use same distance for two sides for spiral effect
                for (l in 1..dist) { // Add all indexes on side
                    when (direction) {
                        'e' -> x++
                        'n' -> y++
                        'w' -> x--
                        's' -> y--
                    }
                    grid.put(index, "$x,$y")
                    index++
                }
                direction = when (direction) { // New direction
                    'e' -> 'n'
                    'n' -> 'w'
                    'w' -> 's'
                    's' -> 'e'
                    else -> throw IllegalStateException()
                }
            }
            dist++
        }

        return grid[361527]!!.split(",").sumOf(String::toInt)
    }

    fun solvePartTwo(): Long {
        return 0
    }
}
