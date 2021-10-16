package com.android.wajvecodingtest.di


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.android.wajvecodingtest.data.RetrofitService
import com.android.wajvecodingtest.data.repository.LinkedinUsersRepositoryImp
import com.android.wajvecodingtest.data.repository.UserDetailRepositoryImp
import com.android.wajvecodingtest.domain.repository.LinkedinUserRepository
import com.android.wajvecodingtest.domain.repository.UserDetailRepository
import com.android.wajvecodingtest.util.Constants
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val mCache = Cache(context.cacheDir, cacheSize)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .cache(mCache) // make your app offline-friendly without a database!
            .connectTimeout(60, TimeUnit.MINUTES)
            .writeTimeout(60, TimeUnit.MINUTES)
            .readTimeout(60, TimeUnit.MINUTES)
            .addNetworkInterceptor(interceptor)
            .addInterceptor { chain ->
                var request = chain.request()
                /* If there is Internet, get the cache that was stored 5 seconds ago.
                 * If the cache is older than 5 seconds, then discard it,
                 * and indicate an error in fetching the response.
                 * The 'max-age' attribute is responsible for this behavior.
                 */
                request = request.newBuilder() //make default to true till i figure out how to inject network status
                    .header("Cache-Control", "public, max-age=" + 5).build()
                chain.proceed(request)
            }
        return client.build()
    }


    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideIsNetworkAvailable(@ApplicationContext context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideLinkedinUserListRepository(
        retrofitService: RetrofitService
    ): LinkedinUserRepository {
        return LinkedinUsersRepositoryImp(retrofitService)
    }
    @Singleton
    @Provides
    fun provideUserDetailRepository(
        retrofitService: RetrofitService
    ): UserDetailRepository {
        return UserDetailRepositoryImp(retrofitService)
    }
}