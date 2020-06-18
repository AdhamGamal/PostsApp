package com.amg.postsapp.hilt

import android.content.Context
import androidx.room.Room
import com.amg.postsapp.data.api.RetrofitService
import com.amg.postsapp.data.room.AppDatabase
import com.amg.postsapp.data.room.PostsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@InstallIn(ApplicationComponent::class)
@Module
object DataModule {

    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "posts.db"
        ).build()
    }

    @Provides
    fun provideDao(database: AppDatabase): PostsDao {
        return database.postsDao()
    }

    @Provides
    fun provideInterceptor(): Interceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC
        return logger
    }

    @Provides
    fun provideGsonConverter(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideClient(interceptor: Interceptor): OkHttpClient {
        // Server is to slow. So, I give it too much time
        return OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient, gsonConverter: GsonConverterFactory): RetrofitService {
        return Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(gsonConverter)
                .client(client)
                .build()
                .create(RetrofitService::class.java)
    }
}