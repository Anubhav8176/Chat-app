package com.anucodes.connecto.di

import com.anucodes.connecto.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import jakarta.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule{

    val supabase = createSupabaseClient(
        supabaseUrl = BuildConfig.SUPABASE_URL,
        supabaseKey = BuildConfig.SUPABASE_ANON_KEY
    ) {
        install(Postgrest)
        install(Auth)
    }

    @Provides
    @Singleton
    fun providesSupabaseAuth(): Auth = supabase.auth

}