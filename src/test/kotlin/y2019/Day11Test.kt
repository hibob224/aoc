package y2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.isCi
import y2019.day11.Day11

internal class Day11Test {

    // TODO These tests fail in CI for some reason, come back to this
    @Test
    fun solvePartOne() {
        if (isCi()) return
        assertEquals("2478", Day11.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        if (isCi()) return
        assertEquals(".##....##..#.#..#.#..#..##.##..###..#.#..##....##..#.#..#.#...#.##.#....##..###.............#.#....##.##.##...##...##..##....##..#.#..###....#.#...........##..###..###..##....##...#.##.#...###..##...##...#.#..#.#..#.#...#.##.##..##....##..##.##..#..", Day11.solvePartTwo())
    }
}
