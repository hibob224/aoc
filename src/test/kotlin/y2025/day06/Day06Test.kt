package y2025.day06

import PuzzleTest
import org.junit.jupiter.api.Test
import template.Puzzle

class Day06Test : PuzzleTest<Long, Long>() {

    override val puzzle: () -> Puzzle<Long, Long> = { Day06() }

    @Test
    fun `test y2025d06`() {
        runTest(5667835681547, 9434900032651)
    }
}