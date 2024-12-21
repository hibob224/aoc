package y2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import y2019.day09.Day9

internal class Day9Test {

    @Test
    fun solvePartOne() {
        assertEquals("3335138414", Day9.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals("49122", Day9.solvePartTwo())
    }
}
