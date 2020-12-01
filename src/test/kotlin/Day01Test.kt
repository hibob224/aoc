import day01.Day01
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day01Test {

    @Test
    fun solvePartOne() {
        assertEquals("41979", Day01.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals("193416912", Day01.solvePartTwo())
    }
}