package br.com.zumbolovsky.fateapp.web

import br.com.zumbolovsky.fateapp.service.CharacterService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing

object Routes {
    fun Application.createRoutes() {
        routing {
            get("/characters") {
                call.respond(CharacterService.findAll(call.receive()))
            }

            post("/characters") {
                call.respond(CharacterService.persist(call.receive()))
            }
        }
    }
}