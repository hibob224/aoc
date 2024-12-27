import io.kotest.matchers.shouldBe
import template.Puzzle

abstract class PuzzleTest<P1, P2> {

    abstract val puzzle: () -> Puzzle<P1, P2>

    fun runTest(p1: P1, p2: P2) {
        val p = puzzle()
        p.solvePartOne() shouldBe p1
        p.solvePartTwo() shouldBe p2
    }
}
