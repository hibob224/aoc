package y2018

import y2018.day07.Day7
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day7Test {

    @Test
    fun solvePartOne() {
        assertEquals("OCPUEFIXHRGWDZABTQJYMNKVSL", Day7.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(991, Day7.solvePartTwo())
    }
}