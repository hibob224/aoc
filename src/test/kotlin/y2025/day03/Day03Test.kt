package y2025.day03

import PuzzleTest
import org.junit.jupiter.api.Test
import template.Puzzle

class Day03Test : PuzzleTest<Long, Long>() {

    override val puzzle: () -> Puzzle<Long, Long> = { Day03() }

    @Test
    fun `test y2025d03`() {
        runTest(17229, 170520923035051)
    }
}