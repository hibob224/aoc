package y2024.day09

import utils.getInputFile
import utils.isEven
import kotlin.time.measureTime

fun main() {
    println("Part one: " + Day09.solvePartOne())
    measureTime {
        println("Part two: " + Day09.solvePartTwo())
    }.also { println("Time: ${it.inWholeSeconds}") }
}

object Day09 {

    private val input = getInputFile(this::class.java.packageName, example = false).readText()

    fun solvePartOne(): Long {
        val disk = buildList {
            for ((index, size) in input.map { it.digitToInt() }.withIndex()) {
                repeat(size) { add(if (index.isEven) index / 2 else -1) }
            }
        }

        var l = 0
        var r = disk.lastIndex
        val fileBlocks = disk.count { it != -1 }

        val defragDisk = buildList {
            while (l < fileBlocks) {
                while (disk[l] != -1 && l < r) add(disk[l++])
                while (disk[r] == -1) r--
                l++
                add(disk[r--])
            }
        }

        return defragDisk.foldIndexed(0) { index, acc, i -> acc + index * i }
    }

    fun solvePartTwo(): Long {
        val disk = buildList {
            for ((index, size) in input.map { it.digitToInt() }.withIndex()) {
                if (index.isEven) {
                    add(Disk.File(index, size))
                } else {
                    add(Disk.EmptySpace(index, size))
                }
            }
        }.toMutableList()
        val files = disk.filterIsInstance<Disk.File>().reversed().toMutableList()
        val movedFileIds = mutableListOf<Int>()

        val defrag = buildList {
            disk.forEach { item ->
                when (item) {
                    is Disk.File -> {
                        if (item.id in movedFileIds) {
                            add(Disk.EmptySpace(-1, item.size))
                        } else {
                            add(item)
                        }
                    }

                    is Disk.EmptySpace -> {
                        var remainingSize = item.size

                        val filesToMove = buildList moveFiles@{
                            files.forEach {
                                if (item.id > it.id || remainingSize == 0) return@moveFiles
                                if (it.size <= remainingSize) {
                                    add(it)
                                    remainingSize -= it.size
                                }
                            }
                        }

                        files.removeAll(filesToMove) // Remove moved files from files we check, make subsequent spaces loop less
                        movedFileIds.addAll(filesToMove.map { it.id }) // Keep track of all moved file IDs, so when we come across that file in the disk we can replace it with blank space
                        add(item.copy(files = filesToMove))
                    }
                }
            }
        }

        return defrag.foldIndexed(0 to 0L) { _, acc, item ->
            (acc.first + item.size) to acc.second + item.checksum(acc.first)
        }.second
    }

    sealed interface Disk {
        data class EmptySpace(
            override val id: Int,
            override val size: Int,
            val files: List<File> = emptyList(),
        ) : Disk {
            override fun checksum(index: Int): Long {
                var sum = 0L
                var indexOffset = 0
                files.forEach {
                    sum += it.checksum(index + indexOffset)
                    indexOffset += it.size
                }
                return sum
            }
        }

        data class File(override val id: Int, override val size: Int) : Disk {
            override fun checksum(index: Int): Long {
                return (0 until size).sumOf { (index + it) * id.div(2).toLong() }
            }
        }

        val id: Int
        val size: Int
        fun checksum(index: Int): Long
    }
}
