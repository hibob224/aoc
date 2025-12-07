package y2025.day07

import PuzzleTest
import org.junit.jupiter.api.Test
import template.Puzzle

class Day07Test : PuzzleTest<Long, Long>() {

    override val puzzle: () -> Puzzle<Long, Long> = { Day07() }

    @Test
    fun `test y2025d07`() {
        runTest(1658, 53_916_299_384_254)
    }
}