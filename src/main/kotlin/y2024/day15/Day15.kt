package y2024.day15

import utils.Point
import utils.getInputFile
import utils.toPointGrid

fun main() {
    println("Part one: " + Day15.solvePartOne())
    println("Part two: " + Day15.solvePartTwo())
}

object Day15 {

    private val instructions: CharArray
    private val gridInput: List<String>

    init {
        val (gridInput, instructionInput) = getInputFile(this::class.java.packageName, example = false)
            .readText()
            .split("\n\n")
            .map { it.split("\n") }
        instructions = instructionInput.joinToString("").toCharArray()
        this.gridInput = gridInput
    }

    fun solvePartOne(): Long {
        val grid = gridInput.toPointGrid().toMutableMap()
        var robot = grid.entries.find { it.value == '@' }!!.key

        instructions.forEach { direction ->
            val movement = generateSequence(robot.getNeighbour(direction).takeUnless { grid.isWall(it) }) { lastPos ->
                if (grid[lastPos] == '.') return@generateSequence null
                lastPos.getNeighbour(direction).takeUnless { grid.isWall(it) }
            }.toList()
            if (movement.isNotEmpty() && movement.any { grid[it] == '.' }) {
                grid[robot] = '.'
                robot = movement.first()
                grid[robot] = '@'

                if (movement.size > 1) {
                    grid[movement.last()] = 'O'
                }
            }
        }

        return grid
            .filterValues { it == 'O' }
            .keys
            .sumOf { (x, y) -> x + y * 100L }
    }

    fun solvePartTwo(): Long {
        val grid = gridInput.map {
            it.map { c ->
                when (c) {
                    '@' -> "@."
                    'O' -> "[]"
                    else -> "$c$c"
                }
            }.joinToString(separator = "")
        }.toPointGrid().toMutableMap()
        var robot = grid.entries.find { it.value == '@' }!!.key
        val horizontalMoves = listOf('<', '>')

        instructions.forEach ins@{ direction ->
            val nextPos = if (direction in horizontalMoves) {
                // Horizontal logic is just the same as P1
                val movement = generateSequence(robot.getNeighbour(direction).takeUnless { grid.isWall(it) }) { lastPos ->
                    if (grid[lastPos] == '.') return@generateSequence null
                    lastPos.getNeighbour(direction).takeUnless { grid.isWall(it) }
                }.toList()
                if (movement.isEmpty() || grid[movement.last()] != '.') return@ins
                movement
                    .reversed()
                    .windowed(2)
                    .forEach { (a, b) ->
                        grid[a] = grid[b]!!
                    }
                movement.first()
            } else {
                // Vertical logic needs to account for larger boxes...
                val movingBoxes = mutableSetOf<Pair<Point, Point>>()
                val searchBoxes = mutableSetOf<Pair<Point, Point>>()
                val nextPos = robot.getNeighbour(direction).takeUnless { grid.isWall(it) } ?: return@ins

                if (grid.isBox(nextPos)) {
                    searchBoxes += grid.fullBox(nextPos)
                }

                while (searchBoxes.isNotEmpty()) {
                    val nextBox = searchBoxes.first()
                    val nextLeft = nextBox.first.getNeighbour(direction)
                    val nextRight = nextBox.second.getNeighbour(direction)

                    if (grid.isWall(nextLeft) || grid.isWall(nextRight)) return@ins // Box will move into a wall, instruction is impossible, exit

                    if (grid.isBox(nextLeft)) {
                        searchBoxes += grid.fullBox(nextLeft)
                    }
                    if (grid.isBox(nextRight)) {
                        searchBoxes += grid.fullBox(nextRight)
                    }

                    movingBoxes += nextBox
                    searchBoxes -= nextBox
                }

                movingBoxes
                    .onEach { (bl, br) ->
                        // Mark all moving box spaces as empty
                        grid[bl] = '.'
                        grid[br] = '.'
                    }
                    .forEach { (bl, br) ->
                        // Update all new box positions
                        grid[bl.getNeighbour(direction)] = '['
                        grid[br.getNeighbour(direction)] = ']'
                    }

                nextPos
            }

            // Move robot
            grid[robot] = '.'
            robot = nextPos
            grid[robot] = '@'
        }

        return grid
            .filterValues { it == '[' }
            .keys
            .sumOf { (x, y) -> x + y * 100L }
    }

    private fun Map<Point, Char>.isWall(point: Point) = get(point) == '#'
    private fun Map<Point, Char>.isBox(point: Point) = get(point) == '[' || get(point) == ']'
    private fun Map<Point, Char>.fullBox(point: Point) = when (get(point)) {
        '[' -> point to point.e
        ']' -> point.w to point
        else -> error("No a box point")
    }
}
