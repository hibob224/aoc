package y2015

import PuzzleTest
import org.junit.jupiter.api.Test
import template.Puzzle

class Day13Test : PuzzleTest<Int, Int>() {

    override val puzzle: () -> Puzzle<Int, Int> = { Day13() }

    @Test
    fun `test y2015d13`() {
        runTest(664, 640)
    }
}
