package run.pod.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/todoapi",
        "org.postgresql.Driver",
        user = "postgres",
        password = "postgres"
    )
}
