package y2025.day12

import PuzzleTest
import org.junit.jupiter.api.Test
import template.Puzzle

class Day12Test : PuzzleTest<Int, Long>() {

    override val puzzle: () -> Puzzle<Int, Long> = { Day12() }

    @Test
    fun `test y2025d12`() {
        runTest(599, 0)
    }
}
