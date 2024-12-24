package y2015.day10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day10Test {

    private val day = Day10()

    @Test
    fun solvePartOne() {
        assertEquals(360154, day.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(5103798, day.solvePartTwo())
    }
}
