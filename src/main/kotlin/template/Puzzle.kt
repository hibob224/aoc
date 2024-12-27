package template

abstract class Puzzle<P1, P2>(
    val year: Int,
    val day: Int,
    val example: Boolean = false,
) {

    val rawInput = InputProvider.provideInput(year, day, example)

    abstract val input: Any

    abstract fun solvePartOne(): P1

    abstract fun solvePartTwo(): P2
}
