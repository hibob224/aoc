package y2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import y2019.day01.Day1

internal class Day1Test {

    @Test
    fun solvePartOne() {
        assertEquals("3232358", Day1.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals("4845669", Day1.solvePartTwo())
    }
}
