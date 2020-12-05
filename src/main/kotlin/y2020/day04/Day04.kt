package y2020.day04

import java.io.File

fun main() {
    println("Part one: ${Day04.solvePartOne()}")
    println("Part two: ${Day04.solvePartTwo()}")
}

object Day04 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')
    private val passports = mutableListOf<Passport>()

    init {
        File("src/main/kotlin/$directory/input.txt").readLines()
            .fold("") { acc, i ->
                if (i.isEmpty()) {
                    passports.add(parsePassport(acc))
                    ""
                } else {
                    "$acc $i"
                }
            }.also {
                passports.add(parsePassport(it))
            }
    }

    fun solvePartOne(): String {
        return passports.count(Passport::isValid).toString()
    }

    fun solvePartTwo(): String {
        return passports.count(Passport::isValidComplicated).toString()
    }

    private fun parsePassport(input: String): Passport {
        val byr = """byr:(\S+)""".toRegex().find(input)?.groupValues?.get(1)
        val iyr = """iyr:(\S+)""".toRegex().find(input)?.groupValues?.get(1)
        val eyr = """eyr:(\S+)""".toRegex().find(input)?.groupValues?.get(1)
        val hgt = """hgt:(\S+)""".toRegex().find(input)?.groupValues?.get(1)
        val hcl = """hcl:(\S+)""".toRegex().find(input)?.groupValues?.get(1)
        val ecl = """ecl:(\S+)""".toRegex().find(input)?.groupValues?.get(1)
        val pid = """pid:(\S+)""".toRegex().find(input)?.groupValues?.get(1)
        val cid = """cid:(\S+)""".toRegex().find(input)?.groupValues?.get(1)
        return Passport(byr, iyr, eyr, hgt, hcl, ecl, pid, cid)
    }

    data class Passport(
        val birthYear: String?,
        val issueYear: String?,
        val expirationYear: String?,
        val height: String?,
        val hairColour: String?,
        val eyeColour: String?,
        val passportId: String?,
        val countryId: String?
    ) {
        companion object {
            private val HEX_REGEX = """^#([\da-fA-F]{6})${'$'}""".toRegex()
            private val PASSWORD_ID_REGEX = """^\d{9}${'$'}""".toRegex()
        }

        val isValid: Boolean
            get() = birthYear.isNullOrEmpty().not() && issueYear.isNullOrEmpty().not()
                    && expirationYear.isNullOrEmpty().not() && height.isNullOrEmpty().not()
                    && hairColour.isNullOrEmpty().not() && eyeColour.isNullOrEmpty().not()
                    && passportId.isNullOrEmpty().not()

        val isValidComplicated: Boolean
            get() {
                birthYear?.toInt()?.takeIf { it in 1920..2002 } ?: return false
                issueYear?.toInt()?.takeIf { it in 2010..2020 } ?: return false
                expirationYear?.toInt()?.takeIf { it in 2020..2030 } ?: return false
                height?.takeIf {
                    ("cm" in it && it.removeSuffix("cm").toInt() in 150..193)
                            || ("in" in it && it.removeSuffix("in").toInt() in 59..76)
                } ?: return false
                if (!HEX_REGEX.matches(hairColour.orEmpty())) {
                    return false
                }
                if (eyeColour !in listOf("amb", "blu", "gry", "brn", "grn", "hzl", "oth")) {
                    return false
                }
                return PASSWORD_ID_REGEX.matches(passportId.orEmpty())
            }
    }
}