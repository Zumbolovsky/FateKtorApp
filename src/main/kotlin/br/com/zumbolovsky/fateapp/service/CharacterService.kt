package br.com.zumbolovsky.fateapp.service

import br.com.zumbolovsky.fateapp.Character
import br.com.zumbolovsky.fateapp.web.CharacterVO
import org.jetbrains.exposed.sql.transactions.transaction

object CharacterService {

    fun findAll() =
        transaction {
            Character.all()
                .map { CharacterVO(it.id.value, it.name, it.rarity) }
                .toCollection(arrayListOf())
        }

    fun persist(request: CharacterVO): CharacterVO {
        return transaction {
            val character: Character
            try {
                when (request.id) {
                    null -> insert(request)
                    else -> update(request)
                }.also { character = it }
                commit()
                return@transaction character.toVO()
            } catch (e: Exception) {
                rollback()
                throw ApplicationException()
            }
        }
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