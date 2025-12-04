package y2025.day04

import PuzzleTest
import org.junit.jupiter.api.Test
import template.Puzzle

class Day04Test : PuzzleTest<Int, Int>() {

    override val puzzle: () -> Puzzle<Int, Int> = { Day04() }

    @Test
    fun `test y2025d04`() {
        runTest(1457, 8310)
    }
}