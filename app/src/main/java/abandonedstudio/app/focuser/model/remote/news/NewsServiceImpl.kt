package abandonedstudio.app.focuser.model.remote.news

import abandonedstudio.app.focuser.model.remote.HttpRoutes
import abandonedstudio.app.focuser.model.remote.news.dto.NewsResponse
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class NewsServiceImpl @Inject constructor(private val client: HttpClient) : NewsService {

    override suspend fun getNews(): List<NewsResponse> {
        return try {
            client.get {
                url(HttpRoutes.POSTS)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}