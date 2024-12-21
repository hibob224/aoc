package y2018


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import y2018.day14.Day14

class Day14Test {

    @Test
    fun solvePartOne() {
        assertEquals("7116398711", Day14.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(20316365, Day14.solvePartTwo())
    }
}