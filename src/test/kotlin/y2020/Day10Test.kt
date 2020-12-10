package y2020

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import y2020.day10.Day10

internal class Day10Test {

    @Test
    fun solvePartOne() {
        assertEquals("1820", Day10.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals("3454189699072", Day10.solvePartTwo())
    }
}