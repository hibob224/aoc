package y2018

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import y2018.day02.Day2


internal class Day2Test {

    @Test
    fun solvePartOne() {
        assertEquals(6370, Day2.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals("rmyxgdlihczskunpfijqcebtv", Day2.solvePartTwo())
    }
}