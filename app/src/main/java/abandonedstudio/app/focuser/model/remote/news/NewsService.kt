package abandonedstudio.app.focuser.model.remote.news

import abandonedstudio.app.focuser.model.remote.news.dto.NewsResponse

interface NewsService {

    suspend fun getNews(): List<NewsResponse>

}
