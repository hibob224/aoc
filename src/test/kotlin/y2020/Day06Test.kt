package y2020

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import y2020.day06.Day06

internal class Day06Test {

    @Test
    fun solvePartOne() {
        assertEquals("7027", Day06.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals("3579", Day06.solvePartTwo())
    }
}