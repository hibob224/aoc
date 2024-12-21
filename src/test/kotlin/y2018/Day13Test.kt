package y2018

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import y2018.day13.Day13

class Day13Test {

    @Test
    fun solvePartOne() {
        assertEquals("38,57", Day13.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals("4,92", Day13.solvePartTwo())
    }
}