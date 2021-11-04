package br.com.zumbolovsky.fateapp.web

import br.com.zumbolovsky.fateapp.domain.Rarity
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
                val v = call.request.queryParameters
                val vo = CharacterVO(
                    v[CharacterVO.id]?.toInt(),
                    v[CharacterVO.name],
                    if (v[CharacterVO.rarity] != null) Rarity.valueOf(v[CharacterVO.rarity]!!) else null
                )
                call.respond(CharacterService.findAll(vo))
            }

            post("/characters") {
                call.respond(CharacterService.persist(call.receive()))
            }
        }
    }
}