package com.gosty.tryoutapp.di

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.database.FirebaseDatabase
import com.gosty.tryoutapp.BuildConfig
import com.gosty.tryoutapp.data.remote.network.ApiService
import com.gosty.tryoutapp.data.repositories.NumerationRepository
import com.gosty.tryoutapp.data.repositories.NumerationRepositoryImpl
import com.gosty.tryoutapp.data.repositories.UserRepository
import com.gosty.tryoutapp.data.repositories.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFirebaseRealtimeDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseCrashlytics(): FirebaseCrashlytics = FirebaseCrashlytics.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuthentication(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideGoogleSignInOptions(): GoogleSignInOptions =
        GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.CLIENT_ID)
            .requestProfile()
            .build()

    @Provides
    @Singleton
    fun provideNumerationRepository(
        apiService: ApiService,
        db: FirebaseDatabase,
        auth: FirebaseAuth,
        crashlytics: FirebaseCrashlytics
    ): NumerationRepository = NumerationRepositoryImpl(apiService, db, auth, crashlytics)

    @Provides
    @Singleton
    fun provideUserRepository(
        auth: FirebaseAuth,
        crashlytics: FirebaseCrashlytics
    ): UserRepository = UserRepositoryImpl(auth, crashlytics)
}