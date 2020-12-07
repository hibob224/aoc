package y2020

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import y2020.day07.Day07

internal class Day07Test {

    @Test
    fun solvePartOne() {
        assertEquals("151", Day07.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals("41559", Day07.solvePartTwo())
    }
}