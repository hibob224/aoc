package y2018

import y2018.day03.Day3
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day3Test {

    @Test
    fun solvePartOne() {
        assertEquals(96569, Day3.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(1023, Day3.solvePartTwo())
    }
}