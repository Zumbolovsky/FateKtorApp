package br.com.zumbolovsky.fateapp.web

import br.com.zumbolovsky.fateapp.domain.Rarity
import kotlinx.serialization.Serializable

@Serializable
data class CharacterVO(
    val id: Int? = null,
    val name: String?,
    val rarity: Rarity?
) {
    constructor() : this(null, null, null)
}
