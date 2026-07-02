package com.fg.chat.messenger.di

import com.fg.chat.messenger.domain.repository.AuthRepository
import com.fg.chat.messenger.domain.repository.ChatRepository
import com.fg.chat.messenger.domain.usecase.auth.LoginUseCase
import com.fg.chat.messenger.domain.usecase.auth.LogoutUseCase
import com.fg.chat.messenger.domain.usecase.auth.SignUpUseCase
import com.fg.chat.messenger.domain.usecase.chat.GetChatsUseCase
import com.fg.chat.messenger.domain.usecase.chat.GetMessagesUseCase
import com.fg.chat.messenger.domain.usecase.chat.SendMessageUseCase
import com.fg.chat.messenger.domain.usecase.profile.GetUserProfileUseCase
import com.fg.chat.messenger.domain.usecase.profile.UpdateStatusUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSignUpUseCase(repository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(repository: AuthRepository): LogoutUseCase {
        return LogoutUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetChatsUseCase(repository: ChatRepository): GetChatsUseCase {
        return GetChatsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetMessagesUseCase(repository: ChatRepository): GetMessagesUseCase {
        return GetMessagesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSendMessageUseCase(repository: ChatRepository): SendMessageUseCase {
        return SendMessageUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetUserProfileUseCase(repository: AuthRepository): GetUserProfileUseCase {
        return GetUserProfileUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateStatusUseCase(repository: AuthRepository): UpdateStatusUseCase {
        return UpdateStatusUseCase(repository)
    }
}
