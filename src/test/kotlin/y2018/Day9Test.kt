package y2018

import y2018.day09.Day9
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day9Test {

    @Test
    fun solvePartOne() {
        assertEquals(374287, Day9.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(3083412635, Day9.solvePartTwo())
    }
}