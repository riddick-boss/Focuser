package abandonedstudio.app.focuser.ui.focusmethods.news

import abandonedstudio.app.focuser.model.remote.news.NewsService
import abandonedstudio.app.focuser.model.remote.news.dto.NewsResponse
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MethodsNewsViewModel @Inject constructor(private val newsService: NewsService) : ViewModel() {

    suspend fun getNews(): List<NewsResponse> {
        return newsService.getNews()
    }

}
