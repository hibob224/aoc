package y2018.day08

import utils.getInputFile
import utils.orZero
import java.util.*

fun main() {
    println("Part one: ${Day8.solvePartOne()}")
    println("Parse two: ${Day8.solvePartTwo()}")
}

object Day8 {

    private val data = getInputFile(this::class.java.packageName).readText().split(" ").map(String::toInt)
    private val dataStack = ArrayDeque(data)
    private val nodes = mutableListOf<Node>()

    init {
        nodes.add(parseNode())
    }

    fun solvePartOne(): Int {
        return (nodes + nodes.flatMap { it.allChildren }).flatMap { it.metaData }.sum()
    }

    fun solvePartTwo(): Int {
        return nodes.first().value()
    }

    private fun parseNode(): Node {
        val childCount = dataStack.pop()
        val metadataCount = dataStack.pop()
        val node = Node()
        repeat(childCount) { node.childNodes.add(parseNode()) }
        repeat(metadataCount) { node.metaData.add(dataStack.pop()) }
        return node
    }
}

class Node {
    val childNodes = mutableListOf<Node>()
    var metaData = mutableListOf<Int>()
    val allChildren: List<Node>
        get() = childNodes + childNodes.flatMap { it.allChildren }

    fun value(): Int =
            if (childNodes.isEmpty()) metaData.sum() else metaData.sumOf { childNodes.getOrNull(it - 1)?.value().orZero() }
}