package br.com.zumbolovsky.fateapp.service

import br.com.zumbolovsky.fateapp.Character
import br.com.zumbolovsky.fateapp.web.CharacterVO
import org.jetbrains.exposed.sql.transactions.transaction

object CharacterService {

    fun findAll() = Character.all()

    fun persist(request: CharacterVO): Character {
        var character: Character? = null
        transaction {
            try {
                when (request.id) {
                    null -> insert(request)
                    else -> update(request)
                }.also { character = it }
                commit()
            } catch (e: Exception) {
                rollback()
                throw ApplicationException()
            }
        }
        return character!!
    }

    private fun update(request: CharacterVO) = Character.new(request.id) {
        name = request.name
        rarity = request.rarity
    }

    private fun insert(request: CharacterVO) = Character.new {
        name = request.name
        rarity = request.rarity
    }
}