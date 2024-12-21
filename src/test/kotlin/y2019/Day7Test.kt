package y2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import y2019.day07.Day7

internal class Day7Test {

    @Test
    fun solvePartOne() {
        assertEquals("14902", Day7.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals("6489132", Day7.solvePartTwo())
    }
}
