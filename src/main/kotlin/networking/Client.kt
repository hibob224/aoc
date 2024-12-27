package networking

import io.github.cdimascio.dotenv.Dotenv
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*

val client = HttpClient(OkHttp) {
    followRedirects = false
    install(UserAgent) {
        agent = "https://github.com/hibob224/aoc/ by contact@galajeu.com"
    }
    defaultRequest {
        cookie("session", Dotenv.load().get("aocSession"))
    }
}
