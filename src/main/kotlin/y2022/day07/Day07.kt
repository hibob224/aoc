package y2022.day07

import java.io.File

fun main() {
    println("Part one: ${Day07.solvePartOne()}")
    println("Part two: ${Day07.solvePartTwo()}")
}

object Day07 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val input = File("src/main/kotlin/$directory/input.txt").readLines()
    private val root = FileSystem.Directory(parent = null, name = "/")
    private val fileRegex = """^(\d+) (.*+)$""".toRegex()
    private val directoryRegex = """^\$ cd (.*+)$""".toRegex()

    init {
        parseFileSystem()
    }

    fun solvePartOne(): Int = root.directories.filter { it.size <= 100000 }.sumOf { it.size }

    fun solvePartTwo(): Int {
        val diskSpace = 70_000_000
        val required = 30_000_000
        val lacking = required - (diskSpace - root.size)

        return root.directories
            .filter { it.size >= lacking }
            .minByOrNull { it.size }!!
            .size
    }

    private fun parseFileSystem() {
        var currentDirectory = root

        input
            .forEach { cliLine ->
                when {
                    cliLine == "$ cd /" -> currentDirectory = root
                    cliLine == "$ cd .." -> currentDirectory = currentDirectory.parent!!
                    cliLine.startsWith("dir ") ->
                        currentDirectory.fileSystem += FileSystem.Directory(currentDirectory, cliLine.split("dir ")[1])

                    directoryRegex.matches(cliLine) -> {
                        currentDirectory = currentDirectory
                            .fileSystem
                            .filterIsInstance<FileSystem.Directory>()
                            .find { it.name == cliLine.split("$ cd ", limit = 2)[1] }!!
                    }

                    fileRegex.matches(cliLine) -> {
                        val (size, name) = cliLine.split(" ", limit = 2)
                        currentDirectory.fileSystem += FileSystem.File(currentDirectory, name, size.toInt())
                    }
                }
            }
    }

    sealed class FileSystem {
        class Directory(
            override val parent: Directory?,
            override val name: String,
            val fileSystem: MutableList<FileSystem> = mutableListOf()
        ) : FileSystem() {
            override val children: List<FileSystem>
                get() = fileSystem.flatMap(FileSystem::children)
            override val size: Int
                get() = children.sumOf(FileSystem::size)

            val directories: List<Directory>
                get() = fileSystem.filterIsInstance<Directory>().flatMap(Directory::directories) + this

            override fun toString(): String = "{name=$name, fileSystem=$fileSystem}"
        }

        class File(
            override val parent: Directory,
            override val name: String,
            override val size: Int,
        ) : FileSystem() {

            override val children: List<FileSystem> = listOf(this)

            override fun toString(): String = "{name=$name, size=$size}"
        }

        abstract val children: List<FileSystem>
        abstract val name: String
        abstract val parent: Directory?
        abstract val size: Int
    }
}
