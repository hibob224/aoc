package y2020

import y2020.day02.Day02
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day02Test {

    @Test
    fun solvePartOne() {
        assertEquals("378", Day02.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals("280", Day02.solvePartTwo())
    }
}