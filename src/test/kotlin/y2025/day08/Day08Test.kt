package y2025.day08

import PuzzleTest
import org.junit.jupiter.api.Test
import template.Puzzle

class Day08Test : PuzzleTest<Long, Long>() {

    override val puzzle: () -> Puzzle<Long, Long> = { Day08() }

    @Test
    fun `test y2025d08`() {
        runTest(115885, 274150525)
    }
}