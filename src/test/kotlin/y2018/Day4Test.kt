package y2018

import y2018.day04.Day4
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day4Test {

    @Test
    fun solvePartOne() {
        assertEquals(95199, Day4.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(7887, Day4.solvePartTwo())
    }
}