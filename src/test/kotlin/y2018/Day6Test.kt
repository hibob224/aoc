package y2018

import y2018.day06.Day6
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day6Test {

    @Test
    fun solvePartOne() {
        assertEquals(4143, Day6.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(35039, Day6.solvePartTwo())
    }
}