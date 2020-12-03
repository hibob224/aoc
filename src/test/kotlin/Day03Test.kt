import day03.Day03
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day03Test {

    @Test
    fun solvePartOne() {
        assertEquals("162", Day03.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals("3064612320", Day03.solvePartTwo())
    }
}