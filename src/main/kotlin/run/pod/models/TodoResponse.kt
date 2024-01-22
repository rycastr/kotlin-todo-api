package run.pod.models

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class TodoResponse(
    val id: String,
    val title: String,
    val done: Boolean
)