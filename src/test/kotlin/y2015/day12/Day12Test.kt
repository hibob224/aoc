package y2015.day12

import PuzzleTest
import org.junit.jupiter.api.Test
import template.Puzzle

class Day12Test : PuzzleTest<Int, Int>() {

    override val puzzle: () -> Puzzle<Int, Int> = { Day12() }

    @Test
    fun `test y2015d12`() {
        runTest(111754, 65402)
    }
}