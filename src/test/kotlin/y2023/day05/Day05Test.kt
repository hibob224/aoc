package y2023.day05

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day05Test {
    @Test
    fun solvePartOne() {
        assertEquals(196167384, Day05.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(125742456, runBlocking { Day05.solvePartTwo() })
    }
}
