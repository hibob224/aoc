package y2024.day24

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day24Test {

    private val day = Day24()

    @Test
    fun solvePartOne() {
        assertEquals(51745744348272, day.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(0, day.solvePartTwo())
    }
}
