package y2025.day10

import PuzzleTest
import org.junit.jupiter.api.Test
import template.Puzzle

class Day10Test : PuzzleTest<Int, Int>() {

    override val puzzle: () -> Puzzle<Int, Int> = { Day10() }

    @Test
    fun `test y2025d10`() {
        runTest(475, 18273)
    }
}
