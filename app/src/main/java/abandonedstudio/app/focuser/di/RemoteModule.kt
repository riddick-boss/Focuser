package abandonedstudio.app.focuser.di

import abandonedstudio.app.focuser.model.remote.news.NewsService
import abandonedstudio.app.focuser.model.remote.news.NewsServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun provideHttpClient() = HttpClient(Android) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    @Provides
    fun provideNewsService(newsServiceImpl: NewsServiceImpl): NewsService = newsServiceImpl

}
