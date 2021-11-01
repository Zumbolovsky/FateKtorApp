package br.com.zumbolovsky.fateapp

import br.com.zumbolovsky.fateapp.domain.Character
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseConfig{
    fun configureDB() {
        Database.connect(
            "jdbc:postgresql://127.0.0.1:5432/fate",
            "org.postgresql.Driver",
            "root",
            "root"
        )
        transaction {
            SchemaUtils.createMissingTablesAndColumns(Character.Characters)
        }
    }
}