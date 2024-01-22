package run.pod.services

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import run.pod.models.Todo
import run.pod.models.TodoRequest
import run.pod.models.Todos
import java.util.*

class TodoService {
    fun createTodo(todoRequest: TodoRequest): Todo? {
        val insertStatement = transaction {
            Todos.insert {
                it[Todos.title] = todoRequest.title
                it[Todos.done] = false
            }
        }

        return insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToTodo)
    }

    fun listTodos(): List<Todo> {
        val todos = transaction {
            Todos.selectAll().map(::resultRowToTodo)
        }

        return todos
    }

    fun updateStatus(todoId: String, newStatus: Boolean): Boolean {
        val rowsAffected = transaction {
            Todos.update({Todos.id eq UUID.fromString(todoId)}) {
                it[Todos.done] = newStatus
            }
        }

        return rowsAffected == 1
    }

    private fun resultRowToTodo(row: ResultRow) = Todo(
        id = row[Todos.id],
        title = row[Todos.title],
        done = row[Todos.done]
    )
}
