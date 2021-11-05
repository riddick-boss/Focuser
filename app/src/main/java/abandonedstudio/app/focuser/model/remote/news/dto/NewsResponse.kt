package abandonedstudio.app.focuser.model.remote.news.dto

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val body: String,
    val title: String,
    val id: Int,
    val userId: Int
)
