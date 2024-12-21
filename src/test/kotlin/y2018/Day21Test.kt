package y2018

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import y2018.day21.Day21

class Day21Test {

    @Test
    fun solvePartOne() {
        assertEquals(12213578, Day21.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(5310683, Day21.solvePartTwo())
    }
}