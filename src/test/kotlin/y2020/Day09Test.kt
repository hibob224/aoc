package y2020

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import y2020.day09.Day09

internal class Day09Test {

    @Test
    fun solvePartOne() {
        assertEquals(1639024365L, Day09.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals("219202240", Day09.solvePartTwo(1639024365L))
    }
}