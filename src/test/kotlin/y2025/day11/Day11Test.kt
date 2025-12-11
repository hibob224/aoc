package y2025.day11

import PuzzleTest
import org.junit.jupiter.api.Test
import template.Puzzle

class Day11Test : PuzzleTest<Long, Long>() {

    override val puzzle: () -> Puzzle<Long, Long> = { Day11() }

    @Test
    fun `test y2025d11`() {
        runTest(708, 0)
    }
}
