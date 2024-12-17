package y2024.day17

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day17Test {
    @Test
    fun solvePartOne() {
        assertEquals("2,7,2,5,1,2,7,3,7", Day17.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(247839002892474, Day17.solvePartTwo())
    }
}
