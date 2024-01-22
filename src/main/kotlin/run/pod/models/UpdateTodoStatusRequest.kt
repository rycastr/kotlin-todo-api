package run.pod.models

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTodoStatusRequest (
    val done: Boolean
)