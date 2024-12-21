package y2018

import y2018.day01.Day1
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day1Test {

    @Test
    fun solvePartOne() {
        assertEquals(400, Day1.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(232, Day1.solvePartTwo())
    }
}