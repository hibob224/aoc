package y2018

import y2018.day19.Day19
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day19Test {

    @Test
    fun solvePartOne() {
        assertEquals(1806, Day19.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(18741072, Day19.solvePartTwo())
    }
}