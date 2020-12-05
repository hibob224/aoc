package y2020.day02

import java.io.File

fun main() {
    println("Part one: ${Day02.solvePartOne()}")
    println("Part two: ${Day02.solvePartTwo()}")
}

object Day02 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val parsedInput: List<PasswordPolicy> =
        File("src/main/kotlin/$directory/input.txt")
            .readLines()
            .map(::PasswordPolicy)

    fun solvePartOne(): String {
        return parsedInput.count(PasswordPolicy::isValid).toString()
    }

    fun solvePartTwo(): String {
        return parsedInput.count(PasswordPolicy::isValidPolicy2).toString()
    }
}

data class PasswordPolicy(val input: String) {

    companion object {
        private val regex = """(\d*)-(\d*) (.): (.*)""".toRegex()
    }

    private var minCount: Int
    private var maxCount: Int
    private var policy: Char
    private var password: String
    val isValid: Boolean
        get() {
            val count = password.count { it == policy }
            return count in minCount..maxCount
        }
    val isValidPolicy2: Boolean
        get() {
            return listOf(minCount, maxCount)
                .filter { password.getOrNull(index = it.dec()) == policy }
                .size == 1
        }

    init {
        val t = regex.find(input)!!
        minCount = t.groupValues[1].toInt()
        maxCount = t.groupValues[2].toInt()
        policy = t.groupValues[3].first()
        password = t.groupValues[4]
    }
}