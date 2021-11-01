package br.com.zumbolovsky.fateapp

import br.com.zumbolovsky.fateapp.web.Routes.createRoutes
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.serialization.json
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty

fun main() {
    embeddedServer(Jetty, 8080, "0.0.0.0") {
        DatabaseConfig.configureDB()
        install(ContentNegotiation) {
            json()
        }
        createRoutes()
    }.start(true)
}