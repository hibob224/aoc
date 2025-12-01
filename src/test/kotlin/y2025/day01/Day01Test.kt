package y2025.day01

import PuzzleTest
import org.junit.jupiter.api.Test
import template.Puzzle

class Day01Test : PuzzleTest<Int, Int>() {

    override val puzzle: () -> Puzzle<Int, Int> = { Day01() }

    @Test
    fun `test y2025d01`() {
        runTest(1154, 6819)
    }
}