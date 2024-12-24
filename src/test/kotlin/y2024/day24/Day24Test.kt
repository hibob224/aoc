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
        assertEquals("bfq,bng,fjp,hkh,hmt,z18,z27,z31", day.solvePartTwo())
    }
}
