package run.pod.models

import kotlinx.serialization.Serializable

@Serializable
data class TodoRequest (
    val title: String
)