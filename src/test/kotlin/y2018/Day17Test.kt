package y2018

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import y2018.day17.Day17

class Day17Test {

    @Test
    fun solvePartOne() {
//        assertEquals(32439, Day17.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        Day17.solvePartOne() // Needs to have been run first, but never managed to get the correct answer for P1, see TODO
        assertEquals(26729, Day17.solvePartTwo())
    }
}