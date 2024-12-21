package y2018

import y2018.day12.Day12
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day12Test {

    @Test
    fun solvePartOne() {
        assertEquals(1184, Day12.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(250000000219, Day12.solvePartTwo())
    }
}