package y2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import y2019.day13.Day13

internal class Day13Test {

    @Test
    fun solvePartOne() {
        assertEquals("253", Day13.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals("12263", Day13.solvePartTwo())
    }
}
