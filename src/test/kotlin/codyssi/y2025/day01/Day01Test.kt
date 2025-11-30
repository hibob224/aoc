package codyssi.y2025.day01

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day01Test {

    private val day = Day01()

    @Test
    fun solvePartOne() {
        assertEquals(-16, day.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(-8, day.solvePartTwo())
    }

    @Test
    fun solvePartThree() {
        assertEquals(-429, day.solvePartThree())
    }
}
