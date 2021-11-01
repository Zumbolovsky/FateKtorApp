package br.com.zumbolovsky.fateapp

import br.com.zumbolovsky.fateapp.domain.Rarity
import br.com.zumbolovsky.fateapp.web.CharacterVO
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

class Character(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Character>(Characters)

    var name by Characters.name
    var rarity by Characters.rarity

    fun toVO(): CharacterVO = CharacterVO(
        id.value,
        name,
        rarity
    )

    object Characters : IntIdTable("PERSONAGENS") {
        val name = varchar("NOME", 100)
        val rarity = enumerationByName("RARIDADE", 16, Rarity::class)
            .check { it.inList(Rarity.values().toList()) }
    }
}
