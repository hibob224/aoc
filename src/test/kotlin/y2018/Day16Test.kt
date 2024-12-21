package y2018

import y2018.day16.Day16
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day16Test {

    @Test
    fun solvePartOne() {
        assertEquals(636, Day16.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(674, Day16.solvePartTwo())
    }
}