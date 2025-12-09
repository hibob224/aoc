package y2025.day09

import PuzzleTest
import org.junit.jupiter.api.Test
import template.Puzzle

class Day09Test : PuzzleTest<Long, Long>() {

    override val puzzle: () -> Puzzle<Long, Long> = { Day09() }

    @Test
    fun `test y2025d09`() {
        runTest(4_740_155_680, 0)
    }
}