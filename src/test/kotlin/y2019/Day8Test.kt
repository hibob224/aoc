package y2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import y2019.day08.Day8

internal class Day8Test {

    @Test
    fun solvePartOne() {
        assertEquals("828", Day8.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals("111101000011100001101111000010100001001000010100000010010000111000001011100010001000010010000101000010000100001001010010100001111011110111000110010000", Day8.solvePartTwo())
    }
}
