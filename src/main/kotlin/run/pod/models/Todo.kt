package run.pod.models

import org.jetbrains.exposed.sql.*
import java.util.UUID

data class Todo(val id: UUID, val title: String, val done: Boolean)

object Todos : Table() {
    val id = uuid("id").autoGenerate()
    val title = varchar("title", 128)
    val done = bool("done")

    override val primaryKey = PrimaryKey(id)
}