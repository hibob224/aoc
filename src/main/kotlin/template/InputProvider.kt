package template

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import networking.client
import java.io.File

object InputProvider {

    fun provideInput(year: Int, day: Int, example: Boolean): String = if (example) {
        File("input/examples/y$year/d$day.txt")
    } else {
        File("input/y$year/d$day.txt")
            .also {
                if (!it.exists()) downloadInput(year, day, it)
            }
    }.readText()

    private fun downloadInput(year: Int, day: Int, out: File) = runBlocking {
        println("Downloading input: Y${year}D$day")
        val response = client.get("https://adventofcode.com/$year/day/$day/input")
        require(response.status.isSuccess())
        out.writeBytes(response.bodyAsBytes())
    }
}
