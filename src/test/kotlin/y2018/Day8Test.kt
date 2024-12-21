package y2018

import y2018.day08.Day8
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day8Test {

    @Test
    fun solvePartOne() {
        assertEquals(42951, Day8.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(18568, Day8.solvePartTwo())
    }
}