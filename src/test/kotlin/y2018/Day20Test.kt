package y2018

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import y2018.day20.Day20

class Day20Test {

    @Test
    fun solvePartOne() {
        assertEquals(3958, Day20.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(8566, Day20.solvePartTwo())
    }
}