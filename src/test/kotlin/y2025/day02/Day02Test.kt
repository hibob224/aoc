package y2025.day02

import PuzzleTest
import org.junit.jupiter.api.Test
import template.Puzzle

class Day02Test : PuzzleTest<Long, Long>() {

    override val puzzle: () -> Puzzle<Long, Long> = { Day02() }

    @Test
    fun `test y2025d02`() {
        runTest(16793817782, 27469417404)
    }
}