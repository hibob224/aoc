package y2018

import y2018.day05.Day5
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day5Test {

    @Test
    fun solvePartOne() {
        assertEquals(9370, Day5.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(6390, Day5.solvePartTwo())
    }
}