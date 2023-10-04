package com.tc.restapi.di

import com.google.gson.Gson
import com.tc.restapi.BuildConfig
import com.tc.restapi.data.remote.ApiDetails
import com.tc.restapi.data.remote.ApiEndpoint
import com.tc.restapi.data.repoistory.Repository
import com.tc.restapi.data.repoistory.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module // module -> responsible for providing definition of injection
@InstallIn(SingletonComponent::class) // dictates the scope of injection
class AppModule {

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun providesHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            ).build()
    }

    @Provides
    fun providesRetrofit(
        client: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiDetails.BASE_URL).also{client ->
                    if(BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }
            }

            .addConverterFactory(GsonConverterFactory.create(gson))

            .build()
    }

    @Provides
    fun providesApiClient(
        retrofit: Retrofit
    ): ApiEndpoint {
        return retrofit.create(ApiEndpoint::class.java)
    }

    @Provides
    fun providesRepository(
        apiClient: ApiEndpoint
    ): Repository {
        return RepositoryImpl(apiClient)
    }


}