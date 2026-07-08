package com.fg.chat.messenger.di

import com.fg.chat.messenger.data.repository.AuthRepositoryImpl
import com.fg.chat.messenger.data.repository.ChatRepositoryImpl
import com.fg.chat.messenger.domain.repository.AuthRepository
import com.fg.chat.messenger.domain.repository.ChatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository
}
