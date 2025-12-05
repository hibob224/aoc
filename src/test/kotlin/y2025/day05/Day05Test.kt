package y2025.day05

import PuzzleTest
import org.junit.jupiter.api.Test
import template.Puzzle

class Day05Test : PuzzleTest<Int, Long>() {

    override val puzzle: () -> Puzzle<Int, Long> = { Day05() }

    @Test
    fun `test y2025d05`() {
        runTest(761, 345755049374932)
    }
}