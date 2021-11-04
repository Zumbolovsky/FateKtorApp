package br.com.zumbolovsky.fateapp.web

import br.com.zumbolovsky.fateapp.domain.Rarity
import kotlinx.serialization.Serializable

@Serializable
data class CharacterVO(
    val id: Int? = null,
    val name: String? = null,
    val rarity: Rarity? = null) {

    companion object {
        const val id: String = "id"
        const val name: String = "name"
        const val rarity: String = "rarity"
    }

    fun hasValues() = id != null || name != null || rarity != null
}
