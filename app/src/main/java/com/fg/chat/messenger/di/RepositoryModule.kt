package com.fg.chat.messenger.di

import com.fg.chat.messenger.data.repository.AuthRepositoryImpl
import com.fg.chat.messenger.data.repository.ChatRepositoryImpl
import com.fg.chat.messenger.domain.repository.AuthRepository
import com.fg.chat.messenger.domain.repository.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideChatRepository(): ChatRepository {
        return ChatRepositoryImpl()
    }
}
