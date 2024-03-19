package ru.nsu.reciepebook.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nsu.reciepebook.BookApp
import ru.nsu.reciepebook.data.api.ApiService
import ru.nsu.reciepebook.data.repository.MainRepository
import ru.nsu.reciepebook.util.Constants
import ru.nsu.reciepebook.util.JwtTokenDataStore
import ru.nsu.reciepebook.util.JwtTokenManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApp(application: Application): BookApp = application as BookApp

    @Provides
    @Singleton
    fun provideMainRepository(api: ApiService): MainRepository =
        MainRepository(api)

    @Provides
    @Singleton
    fun provideApiService(client: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()
    @Provides
    @Singleton
    fun provideJtwManager(app: Application): JwtTokenManager = JwtTokenDataStore(app)
}
