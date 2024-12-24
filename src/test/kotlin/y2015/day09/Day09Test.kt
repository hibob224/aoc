package y2015.day09

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day09Test {

    private val day = Day09()

    @Test
    fun solvePartOne() {
        assertEquals(117, day.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(909, day.solvePartTwo())
    }
}
