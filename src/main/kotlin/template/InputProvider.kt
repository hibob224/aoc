package template

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import networking.client
import java.io.File

object InputProvider {

    fun provideInput(year: Int, day: Int, example: Boolean): String {
        val dayStr = "$day".padStart(2, '0')
        return if (example) {
            File("src/main/kotlin/y$year/day$dayStr/example.txt")
        } else {
            File("src/main/kotlin/y$year/day$dayStr/input.txt")
                .also {
                    if (!it.exists()) downloadInput(year, day, it)
                }
        }.readText().trim()
    }

    private fun downloadInput(year: Int, day: Int, out: File) = runBlocking {
        println("Downloading input: Y${year}D$day")
        val response = client.get("https://adventofcode.com/$year/day/$day/input")
        require(response.status.isSuccess())
        out.writeBytes(response.bodyAsBytes())
    }
}
