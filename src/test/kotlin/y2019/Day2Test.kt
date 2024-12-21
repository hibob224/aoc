package y2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import y2019.day02.Day2

internal class Day2Test {

    @Test
    fun solvePartOne() {
        assertEquals("3765464", Day2.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals("7610", Day2.solvePartTwo())
    }
}
