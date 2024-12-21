package y2018

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import y2018.day18.Day18

class Day18Test {

    @Test
    fun solvePartOne() {
        assertEquals(543312, Day18.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(199064, Day18.solvePartTwo())
    }
}