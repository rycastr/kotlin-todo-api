package run.pod.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import run.pod.models.*
import run.pod.services.TodoService

fun Application.configureRouting() {
    routing {
        route("/api/todos") {
            val todoService = TodoService()

            post {
                val request = call.receive<TodoRequest>()

                val result = todoService.createTodo(todoRequest = request)

                if (result !is Todo) {
                    call.respond(HttpStatusCode.BadRequest, ErrorResponse("Fail to create todo."))
                } else {
                    call.respond(
                        HttpStatusCode.Created, TodoResponse(
                            id = result.id.toString(),
                            title = result.title,
                            done = result.done
                        )
                    )
                }
            }

            get {
                val result = todoService.listTodos()

                call.respond(HttpStatusCode.OK, result.map {
                    TodoResponse(
                        id = it.id.toString(),
                        title = it.title,
                        done = it.done
                    )
                })
            }

            patch("/{todoId}") {
                val todoId = call.parameters["todoId"]
                val request = call.receive<UpdateTodoStatusRequest>()

                if (todoId !is String || todoId.isEmpty()) {
                    call.respond(HttpStatusCode.BadRequest, ErrorResponse("Missing todo ID"))
                } else {
                    val result = todoService.updateStatus(todoId, request.done)

                    if (result) {
                        call.respond(HttpStatusCode.NoContent)
                    } else {
                        call.respond(HttpStatusCode.BadRequest, ErrorResponse("Fail to update todo"))
                    }
                }
            }
        }
    }
}
