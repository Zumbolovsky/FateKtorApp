package br.com.zumbolovsky.fateapp.service

import br.com.zumbolovsky.fateapp.domain.Character
import br.com.zumbolovsky.fateapp.domain.Character.Characters.id
import br.com.zumbolovsky.fateapp.domain.Character.Characters.name
import br.com.zumbolovsky.fateapp.domain.Character.Characters.rarity
import br.com.zumbolovsky.fateapp.web.CharacterVO
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
object CharacterService {

    fun findAll(request: CharacterVO) =
        transaction {
            find(request).notForUpdate()
                .map { CharacterVO(it.id.value, it.name, it.rarity) }
                .toCollection(arrayListOf())
        }

    private fun find(request: CharacterVO) =
        when {
            request.hasValues() -> findWithParameters(request)
            else -> Character.all()
        }.orderBy(Pair(id, SortOrder.ASC))

    private fun findWithParameters(request: CharacterVO) =
        Character.find {
            val operations = arrayListOf<Op<Boolean>>()
            when {
                request.id != null -> operations += id eq request.id
            }
            when {
                request.name != null -> operations += name eq request.name
            }
            when {
                request.rarity != null -> operations += rarity eq request.rarity
            }
            operations.reduce { o1, o2 -> o1 and o2 }
        }

    fun persist(request: CharacterVO): CharacterVO =
        transaction {
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

    private fun update(request: CharacterVO): Character {
        val character = Character.findById(request.id!!) ?: throw ApplicationException()
        character.name = request.name!!
        character.rarity = request.rarity!!
        return character
    }

    private fun insert(request: CharacterVO) =
        Character.new {
            name = request.name!!
            rarity = request.rarity!!
        }
}
